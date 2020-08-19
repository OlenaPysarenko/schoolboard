package com.ua.schoolboard.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class BalanceEntity {
/*    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long balanceId;*/
    @Column
    private Integer amount;
    @Column
    private Integer classesPaid;
    @OneToMany
    private List<PaymentEntity> payments = new ArrayList<>();

}
