package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.PaymentEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.PaymentBO;
import com.ua.schoolboard.service.model.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentEntityMapper {

    PaymentEntity toPaymentEntity(PaymentBO source);
    //@Mapping(target ="user",ignore = true)
    PaymentBO toPaymentBO(PaymentEntity source);
    @Mapping(target = "balance", ignore = true)
    UserBO toUserBO(UserEntity source);


}
