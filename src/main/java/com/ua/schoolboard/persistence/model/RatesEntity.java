package com.ua.schoolboard.persistence.model;

import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Role;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity(name = "rates")
public class RatesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ratesId;

    @Column
    private String rateDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column
    private Language language;

    @Column
    private Integer indRate;

    @Column
    private Integer groupRate;

    @Column
    private Integer groupRateMin;

    @Column
    private Integer groupRatePerStudent;
}
