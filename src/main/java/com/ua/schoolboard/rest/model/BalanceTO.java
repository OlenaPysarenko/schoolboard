package com.ua.schoolboard.rest.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BalanceTO {

    private Integer amount = 0;

    private Integer classesPaid=0;

    private List<PaymentTO> payments = new ArrayList<>();

    public PaymentTO createPayment(UserTO user, Integer amount) {
        PaymentTO payment = new PaymentTO();
        payment.setUser(user);
        payment.setAmount(amount);
        payment.setDate(new Date());
        return payment;
    }


}
