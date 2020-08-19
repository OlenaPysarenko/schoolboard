package com.ua.schoolboard.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BalanceBO {
    //private Long balanceId;

    private Integer amount;

    private Integer classesPaid;

    private List<PaymentBO> payments = new ArrayList<>();
}
