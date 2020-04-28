package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPersistMapper {

    UserEntity toUserEntity(UserBO source);

    UserBO toUserBO(UserEntity source);

    List<UserEntity> toUserEntities(List<UserBO> source);

    List<UserBO> toUserBOs(List<UserEntity> source);
}
