package com.ua.schoolboard.service.model;

import lombok.Data;

@Data
public class BalanceBO {

    private Integer amount;

    private Integer classesPaid;

    public BalanceBO(){
        this.amount=0;
        this.classesPaid=0;
    }
}
