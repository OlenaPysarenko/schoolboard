package com.ua.schoolboard.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class BalanceEntity {

    @Column
    private Integer amount;
    @Column
    private Integer classesPaid;

}
