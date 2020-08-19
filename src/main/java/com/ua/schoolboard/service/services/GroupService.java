package com.ua.schoolboard.service.services;

import com.ua.schoolboard.persistence.repos.GroupRepository;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.GroupMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.GroupBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMapper mapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GroupRepository repository;

    public void createNewGroup(GroupTO groupTO) {
        GroupBO groupBO = mapper.toGroupBO(groupTO);
        repository.createNewGroup(groupBO);
    }

    public List<GroupTO> listOfIndividualStudents() {
        List<GroupBO> groupBOS = repository.listOfGroupBO();
        List<GroupBO> individuals = groupBOS.stream().filter(g -> g.getGroupType().equals(GroupType.INDIVIDUAL)).collect(Collectors.toList());
        return mapper.toGroupTOs(individuals);
    }

    public List<GroupTO> listOfGroups() {
        List<GroupBO> groupBOS = repository.listOfGroupBO();
        List<GroupBO> groups = groupBOS.stream().filter(g -> g.getGroupType().equals(GroupType.GROUP)).collect(Collectors.toList());
        return mapper.toGroupTOs(groups);

    }

    public List<GroupTO> listByTeacher(Long teacherId) {
        List<GroupBO> groupBOS = repository.listByTeacher(teacherId);
        return mapper.toGroupTOs(groupBOS);
    }

    public GroupTO updateGroup(GroupTO groupTO) {
        GroupBO groupBO = mapper.toGroupBO(groupTO);
        return mapper.toGroupTO(repository.update(groupBO));
    }

    public List<GroupTO> getAll() {
        List<GroupBO> groupBOS = repository.listOfGroupBO();
        return mapper.toGroupTOs(groupBOS);
    }

    //TODO add students to group instead of setting them every time
    public void assignUserToGroup(GroupTO groupTO, Long userId) {
        UserBO userBO = userRepository.findUserById(userId);
       /* if (userBO.getRole().equals(Role.STUDENT)) {
            UpdateStudentTO updateStudentTO = userMapper.toStudentTO(userBO);
            if (!groupTO.getStudents().isEmpty()) {
                groupTO.getStudents().add(updateStudentTO);
            } else {
                groupTO.setStudents(Collections.singletonList(updateStudentTO));
            }
        } else if (userBO.getRole().equals(Role.TEACHER)) {
            UpdateTeacherTO updateTeacherTO = userMapper.toTeacherTO(userBO);
            groupTO.setTeacher(updateTeacherTO);
        } else {
            //TODO this is a stub for further development of other possible roles
        }*/
        userBO.getRole().updateUser(groupTO, userBO, userMapper::toTeacherTO, userMapper::toStudentTO);
        repository.assignUserToGroup((mapper.toGroupBO(groupTO)));
    }

    public GroupTO getGroupByName(String groupName) {
        GroupBO groupByName = repository.getGroupByName(groupName);
        return mapper.toGroupTO(groupByName);

    }

    public GroupTO getGroupById(long groupId) {
        GroupBO groupById = repository.getGroupById(groupId);
        return mapper.toGroupTO(groupById);
    }
}
