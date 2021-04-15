package com.ua.schoolboard.rest.model;

import com.ua.schoolboard.service.model.UserBO;
import lombok.Data;

import java.util.Date;
@Data
public class PaymentTO {
    private Long paymentId;
    private UserTO userFrom;
    private UserTO userTo;
    private Date date;
    private Integer amount;
}
