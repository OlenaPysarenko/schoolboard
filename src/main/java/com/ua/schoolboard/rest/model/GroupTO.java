package com.ua.schoolboard.rest.model;

import com.ua.schoolboard.persistence.Duration;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupTO {
    private Long groupId;
    private String groupName;
    private GroupType groupType;
    private Language language;
    private Levels lvl;
    private String bookName;
    private Duration duration;
    private List<UpdateStudentTO> students = new ArrayList<>();
    private UpdateTeacherTO teacher;
    private List<ClassSessionTO> classesCovered = new ArrayList<>();
    private boolean active;


    @Override
    public String toString() {
        return "GroupTO{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupType=" + groupType +
                ", language=" + language +
                ", lvl=" + lvl +
                ", bookName='" + bookName + '\'' +
                ", duration=" + duration +
                ", students=" + students +
                ", teacher=" + teacher +
                ", classesCovered=" + classesCovered.size() +
                ", active=" + active +
                '}';
    }
}
