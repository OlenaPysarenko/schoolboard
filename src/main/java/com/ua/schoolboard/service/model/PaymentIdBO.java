package com.ua.schoolboard.service.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PaymentIdBO {
    private Long userId;
    private Long paymentId;
}
