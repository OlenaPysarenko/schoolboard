package com.ua.schoolboard.service.model;

import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Role;
import lombok.Data;


@Data
public class RatesBO {
    private Long ratesId;
    private String rateDescription;
    private Role role;
    private Language language;
    private Integer indRate;
    private Integer groupRate;
    private Integer groupRateMin;
    private Integer groupRatePerStudent;
}
