package com.ua.schoolboard.rest.model;

import lombok.Data;

import java.util.Date;
@Data
public class PaymentTO {
    private Long paymentId;
    private UserTO user;
    private Date date;
    private Integer amount;
}
