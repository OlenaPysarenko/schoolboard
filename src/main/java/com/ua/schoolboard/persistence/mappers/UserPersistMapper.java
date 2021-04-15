package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.PaymentEntity;
import com.ua.schoolboard.persistence.model.RatesEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.PaymentBO;
import com.ua.schoolboard.service.model.RatesBO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPersistMapper {

    @Mapping(target = "rates", source = "rates")
    @Mapping(target = "joinedDate", source = "joinedDate")
    @Mapping(target = "role", source = "role")
    UserEntity toUserEntity(UserBO source);

    @Mapping(target = "rates", source = "rates")
    UserBO toUserBO(UserEntity source);


    List<RatesEntity> toRatesEntities(List<RatesBO> source);

    List<UserEntity> toUserEntities(List<UserBO> source);

    List<UserBO> toUserBOs(List<UserEntity> source);

}
