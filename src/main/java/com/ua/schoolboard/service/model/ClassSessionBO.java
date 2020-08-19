package com.ua.schoolboard.service.model;

import com.ua.schoolboard.persistence.Hometask;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.GroupBO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ClassSessionBO {
    private long classId;
    private GroupBO group;
    private UserBO teacher;
    private List<UserBO> students = new ArrayList<>();
    private int classesCovered;
    private Date classDate;
    private String hometask;
}

