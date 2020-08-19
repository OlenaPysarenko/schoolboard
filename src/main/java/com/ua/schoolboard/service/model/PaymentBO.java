package com.ua.schoolboard.service.model;


import lombok.Data;

import java.util.Date;
@Data
public class PaymentBO {
    private Long paymentId;
    private UserBO user;
    private Date date;
    private Integer amount;
}
