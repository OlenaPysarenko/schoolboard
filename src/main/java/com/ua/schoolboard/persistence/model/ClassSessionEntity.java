package com.ua.schoolboard.persistence.model;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
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

    @Override
    public String toString() {
        return "ClassSessionEntity{" +
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
