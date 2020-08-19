package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.model.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepo extends CrudRepository<PaymentEntity,Long> {

}
