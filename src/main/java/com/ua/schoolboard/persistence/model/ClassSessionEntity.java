package com.ua.schoolboard.persistence.model;

import com.ua.schoolboard.persistence.Hometask;
import com.ua.schoolboard.service.model.GroupBO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "class")
public class ClassSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classId;
    @ManyToOne
    private GroupEntity group;
    @ManyToOne
    private UserEntity teacher;
    @ManyToMany
    private List<UserEntity> students =  new ArrayList<>();
    @Column
    private int classesCovered;
    @Column
    private Date classDate;
    @Column
    private String hometask;

}
