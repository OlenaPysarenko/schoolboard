package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.persistence.model.PaymentEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.GroupBO;
import com.ua.schoolboard.service.model.PaymentBO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GroupEntityMapper {

     GroupEntity toGroupEntity(GroupBO source);

     GroupBO toGroupBO(GroupEntity source);

     List<GroupBO> toGroupBOs(List<GroupEntity> source);

     List<GroupEntity> toGroupEntities(List<GroupBO> source);
     @Mapping(target ="user",ignore = true)
     PaymentBO toPaymentBO(PaymentEntity source);
//     @Mapping(target = "balance", ignore = true)
//     UserEntity toUserEntity(UserBO source);
}
