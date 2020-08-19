package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.PaymentTO;
import com.ua.schoolboard.service.model.PaymentBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentBO toPaymentBO(PaymentTO source);
    PaymentTO toPaymentTO(PaymentBO source);
}
