package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.UpdateAdminTO;
import com.ua.schoolboard.rest.model.UpdateStudentTO;
import com.ua.schoolboard.rest.model.UpdateTeacherTO;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.model.GroupBO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserTO toUserTO(UserBO source);

    UpdateStudentTO toStudentTO(UserBO source);


    UpdateAdminTO toAdminTO(UserBO source);

    //@UserInfo
    UpdateTeacherTO toTeacherTO(UserBO userBO);


    UserBO toUserBO(UpdateStudentTO source);

    //List<UserBO> toUpdUserBOs(List<UpdateStudentTO>source);

    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "rates", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "surname", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "nickname", ignore = true)
    @Mapping(target = "birthDay", ignore = true)
    @Mapping(target = "joinedDate", source = "joinedDate")
    @Mapping(target = "role", source = "role")
    UserBO toUserBO(UserTO source);


    List<UpdateTeacherTO> toTeacherTOs(List<UserBO> source);

    List<UpdateStudentTO> toStudentTOs(List<UserBO> source);

   // List<UserTO> toUserTOs(List<UserBO>source);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "joinedDate", ignore = true)
    UserBO toUpdateStudentBO(UpdateStudentTO source);


    UserBO toUpdateTeacherBO(UpdateTeacherTO source);


    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "surname", ignore = true)
    @Mapping(target = "nickname", ignore = true)
    @Mapping(target = "userId", source = "userId")
    UserBO toUserBO(UpdateAdminTO source);

    List<GroupBO> toGroupBOs(List<GroupBO> source);
}
