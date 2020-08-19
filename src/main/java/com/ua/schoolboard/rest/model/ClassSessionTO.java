package com.ua.schoolboard.rest.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data

public class ClassSessionTO {
    private int classId;
    private GroupTO group;
    private UpdateTeacherTO teacher;
    private List<UpdateStudentTO> students = new ArrayList<>();
    private int classesCovered;
    private Date classDate;
    private String hometask;


    public Integer calculateWageForClass() {
        return students.size() > 1
                ? calculateGroupWage()
                : teacher.getRates().iterator().next().getIndRate(); //TODO Assume we only have one rate per teacher, subject to further investigation
    }

    private Integer calculateGroupWage() {
        // for group = min rate + q-ty student per class * rate per/student
        TeacherRatesTO rate = teacher.getRates().iterator().next();
        return (students.size() * rate.getGroupRatePerStudent()) + rate.getGroupRateMin();
    }

    @Override
    public String toString() {
        return "ClassSessionTO{" +
                "classId=" + classId +
                ", group=" + group +
                ", teacher=" + teacher +
                ", students=" + students +
                ", classesCovered=" + classesCovered +
                ", classDate=" + classDate +
                ", hometask='" + hometask + '\'' +
                '}';
    }
}
