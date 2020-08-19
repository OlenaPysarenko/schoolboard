package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.rest.model.GroupType;
import com.ua.schoolboard.rest.model.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepo extends CrudRepository<GroupEntity, Long> {
    Optional<GroupEntity> findByGroupType(GroupType groupType);

    Optional<GroupEntity> findUserByLanguage(Language language);

    Optional<GroupEntity> findByTeacher(UserEntity teacher);

    Optional<GroupEntity> findByGroupName(String groupName);

    List<GroupEntity> findAll();

    List<GroupEntity> findAllByTeacher(UserEntity teacher);

}
