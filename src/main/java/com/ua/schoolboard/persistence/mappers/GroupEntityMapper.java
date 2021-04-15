package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.service.model.GroupBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupEntityMapper {

     GroupEntity toGroupEntity(GroupBO source);

     @Mapping(target = "classesCovered", ignore = true)
     GroupBO toGroupBO(GroupEntity source);

     List<GroupBO> toGroupBOs(List<GroupEntity> source);

     List<GroupEntity> toGroupEntities(List<GroupBO> source);

}
