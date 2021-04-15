package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.model.ClassSessionEntity;
import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepo extends CrudRepository<ClassSessionEntity, Long> {
    Optional<ClassSessionEntity> findByTeacher(UserEntity teacher);

    List<ClassSessionEntity> findAllByTeacher(UserEntity teacher);

    Optional<ClassSessionEntity> findByClassId(Long classId);

    List<ClassSessionEntity> findAllByGroup(GroupEntity groupEntity);
}
