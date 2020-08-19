package com.ua.schoolboard.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@EqualsAndHashCode(callSuper = false)
public class TeacherRatesTO extends RatesTO {
    private Integer groupRateMin;
    private Integer groupRatePerStudent;


    public int calculateGroupWage(GroupType type, int studentsPresent, int classesCovered) {
        return type == GroupType.GROUP
                ? (this.groupRateMin + (this.groupRatePerStudent * studentsPresent)) * classesCovered
                : classesCovered * this.getIndRate();
    }

}
