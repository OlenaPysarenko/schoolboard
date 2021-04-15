package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.mappers.PaymentEntityMapper;
import com.ua.schoolboard.service.model.PaymentBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentRepository {
    private final PaymentEntityMapper entityMapper;
    private final PaymentRepo paymentRepo;

    public PaymentBO register(PaymentBO paymentBO){
       return entityMapper.toPaymentBO(paymentRepo.save(entityMapper.toPaymentEntity(paymentBO)));
    }

    public PaymentBO getPaymentById(long paymentId){
        return entityMapper.toPaymentBO(paymentRepo.findById(paymentId).get());
    }
}
