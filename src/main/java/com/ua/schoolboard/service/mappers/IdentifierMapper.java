package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.UpdateTeacherTO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IdentifierMapper {
    @Mapping(target = "rates", qualifiedBy = UserInfo.class)
    UpdateTeacherTO map(UserBO source);
}
