package com.ua.schoolboard.rest.model;

import lombok.Data;

@Data
public class RatesTO {
    private Long ratesId;
    private String rateDescription;
    private Role role;
    private Language language;
    private Integer indRate;
    private Integer groupRate;

    public int calculateCovered(Integer sum, GroupType type) {
        return type == GroupType.GROUP
                ? sum / this.getGroupRate()
                : sum / this.getIndRate();
    }

}