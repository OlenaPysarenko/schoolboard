package com.ua.schoolboard.persistence;

import com.ua.schoolboard.persistence.model.UserEntity;

import java.util.Date;
import java.util.List;

public class ClassSession {
    private int classId;
    private Group groupName;
    UserEntity teacher;
    List<UserEntity> students;
    private int classesCovered;
    Date classDate;
    Hometask hometask;

}

