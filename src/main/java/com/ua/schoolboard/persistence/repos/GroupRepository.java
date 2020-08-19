package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.mappers.GroupEntityMapper;
import com.ua.schoolboard.persistence.mappers.UserPersistMapper;
import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.GroupBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getSupplierException;

@Service
@RequiredArgsConstructor
public class GroupRepository {
    private final GroupRepo groupRepository;
    private final GroupEntityMapper groupMapper;
    private final UserRepository userRepository;
    private  final UserPersistMapper userPersistMapper;

    public void createNewGroup(GroupBO groupBO) {
        if (groupRepository.findByGroupName(groupBO.getGroupName()).isPresent()) {
            throw getException(GROUP_ALREADY_EXISTS);
        }
        GroupEntity groupEntity = groupMapper.toGroupEntity(groupBO);
        groupRepository.save(groupEntity);
    }

    public List<GroupBO> listOfGroupBO() {
        return groupMapper.toGroupBOs(groupRepository.findAll());
    }

    public void assignUserToGroup(GroupBO groupBO) {
        GroupEntity groupEntity = groupMapper.toGroupEntity(groupBO);
        groupRepository.save(groupEntity);
    }

    public GroupBO getGroupByName(String groupName) {
        GroupEntity groupEntity = groupRepository.findByGroupName(groupName).orElseThrow(getSupplierException(GROUP_NOT_FOUND));
        return groupMapper.toGroupBO(groupEntity);
    }

    public GroupBO getGroupById(long groupId) {
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(getSupplierException(GROUP_NOT_FOUND));
        return groupMapper.toGroupBO(groupEntity);
    }

    public GroupBO update(GroupBO groupBO) {
        GroupEntity persistedEntity = groupRepository.findById(groupBO.getGroupId()).orElseThrow(getSupplierException(GROUP_NOT_FOUND));
        GroupEntity groupEntity = groupMapper.toGroupEntity(groupBO);
        groupEntity.setClassesCovered(persistedEntity.getClassesCovered());
        groupEntity.setDuration(groupEntity.getDuration());
        groupEntity.setGroupType(persistedEntity.getGroupType());
        groupEntity.setLanguage(persistedEntity.getLanguage());
        groupEntity.setStudents(persistedEntity.getStudents());
        groupEntity.setTeacher(persistedEntity.getTeacher());
        return groupMapper.toGroupBO(groupRepository.save(groupEntity));
    }
    public List<GroupBO> listByTeacher(Long teacherId){
        UserBO userById = userRepository.findUserById(teacherId);
        UserEntity userEntity = userPersistMapper.toUserEntity(userById);
        List<GroupEntity> allByTeacher = groupRepository.findAllByTeacher(userEntity);
        return groupMapper.toGroupBOs(allByTeacher);
    }

}
