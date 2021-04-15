package com.ua.schoolboard.rest.model;


import lombok.Data;

import java.util.Date;

@Data
public class BalanceTO {

    private Integer amount = 0;

    private Integer classesPaid = 0;

    public PaymentTO createPayment(UserTO userTO, UserTO userFrom, Integer amount) {
        PaymentTO payment = new PaymentTO();
        payment.setUserTo(userTO);
        payment.setUserFrom(userFrom);
        payment.setAmount(amount);
        payment.setDate(new Date());
        return payment;
    }


}
