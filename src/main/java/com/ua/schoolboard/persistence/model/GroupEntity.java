package com.ua.schoolboard.persistence.model;

import com.ua.schoolboard.persistence.Duration;
import com.ua.schoolboard.rest.model.GroupType;
import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Levels;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;

    @Enumerated(EnumType.STRING)
    @Column
    private GroupType groupType;

    @Column
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column
    private Levels lvl;

    @Column
    private String bookName;

    @Embedded
    private Duration duration;

    @OneToMany
    private List<UserEntity> students =  new ArrayList<>();

    @ManyToOne
    private UserEntity teacher;

    @OneToMany
    private List<ClassSessionEntity> classesCovered =  new ArrayList<>();

}
