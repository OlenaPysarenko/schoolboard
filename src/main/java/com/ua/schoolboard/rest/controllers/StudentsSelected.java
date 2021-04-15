package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.UpdateStudentTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class StudentsSelected {
    private List<UpdateStudentTO> students = new ArrayList<>();

    public void addStudents(UpdateStudentTO student){
        this.students.add(student);
    }
}
