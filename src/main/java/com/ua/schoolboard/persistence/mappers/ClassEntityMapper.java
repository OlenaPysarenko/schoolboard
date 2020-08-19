package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.ClassSessionEntity;
import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.rest.model.GroupTO;
import com.ua.schoolboard.service.model.ClassSessionBO;
import com.ua.schoolboard.service.model.GroupBO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses=UserPersistMapper.class)
public interface ClassEntityMapper {
    ClassSessionEntity toClassEntity(ClassSessionBO source);

    ClassSessionBO toClassBO(ClassSessionEntity source);

    List<ClassSessionBO> toClassesBOs(List<ClassSessionEntity> source);

    List<ClassSessionEntity> toClassesEntities(List<ClassSessionBO> source);

    @Mapping(target = "classesCovered", ignore = true)
    GroupEntity toGroupEntity(GroupBO source);
}
