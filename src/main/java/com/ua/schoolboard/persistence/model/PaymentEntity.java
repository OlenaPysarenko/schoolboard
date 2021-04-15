package com.ua.schoolboard.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    @OneToOne
    private UserEntity userFrom;
    @OneToOne
    private UserEntity userTo;
    @Column
    private Date date;
    @Column
    private Integer amount;
}
