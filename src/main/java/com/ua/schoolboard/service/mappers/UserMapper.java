package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserTO toUserTO(UserBO source);

    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "nickname", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "joinedDate", ignore = true)
    @Mapping(target = "birthDay", ignore = true)
    @Mapping(target = "balance", ignore = true)
    UserBO toUserBO(UserTO source);

    List<UserTO> toUserTOs(List<UserBO>source);
}
