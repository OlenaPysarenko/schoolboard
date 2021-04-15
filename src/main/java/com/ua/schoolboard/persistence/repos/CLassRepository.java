package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.mappers.ClassEntityMapper;
import com.ua.schoolboard.persistence.mappers.GroupEntityMapper;
import com.ua.schoolboard.persistence.model.ClassSessionEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.ClassSessionBO;
import com.ua.schoolboard.service.model.GroupBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getSupplierException;

@Service
@RequiredArgsConstructor
public class CLassRepository {
    private final ClassRepo repo;
    private final ClassEntityMapper classEntityMapper;
    private final GroupRepo groupRepo;
    private final UserRepo userRepo;
    private final GroupEntityMapper groupMapper;

    public List<ClassSessionBO> getClassesByTeacher(Long teacherId) throws CustomException {
        UserEntity teacher = userRepo.findById(teacherId).orElseThrow(getSupplierException(USER_NOT_FOUND));
        List<ClassSessionEntity> teacherClasses = repo.findAllByTeacher(teacher);
        return classEntityMapper.toClassesBOs(teacherClasses);
    }

    public ClassSessionBO getById(Long classId) throws CustomException {
        ClassSessionEntity classSessionEntity = repo.findByClassId(classId).orElseThrow(getSupplierException(CLASS_NOT_FOUND));
        return classEntityMapper.toClassBO(classSessionEntity);
    }

    public void register(ClassSessionBO classBO) throws CustomException {
        if (repo.findByClassId(classBO.getClassId()).isPresent()) {
            throw getException(CLASS_ALREADY_EXISTS);
        }
        ClassSessionEntity classSessionEntity = classEntityMapper.toClassEntity(classBO);
        repo.save(classSessionEntity);
    }
    public void edit(ClassSessionBO classBO){
        repo.save(classEntityMapper.toClassEntity(classBO));
    }

    public List<ClassSessionBO> getAllByGroup(GroupBO groupBO){
        return classEntityMapper.toClassesBOs(repo.findAllByGroup(groupMapper.toGroupEntity(groupBO)));
    }
}
