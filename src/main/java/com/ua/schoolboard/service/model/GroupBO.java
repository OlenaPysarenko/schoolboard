package com.ua.schoolboard.service.model;

import com.ua.schoolboard.persistence.Duration;
import com.ua.schoolboard.persistence.model.ClassSessionEntity;
import com.ua.schoolboard.rest.model.GroupType;
import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Levels;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Data
public class GroupBO {
    private Long groupId;
    private String groupName;
    private GroupType groupType;
    private Language language;
    private Levels lvl;
    private String bookName;
    private Duration duration;
    private List<UserBO> students = new ArrayList<>();
    private UserBO teacher;
    private List<ClassSessionBO> classesCovered = new ArrayList<>();

 /*   public List<ClassSessionBO> getClassesCovered() {
        List classesCovered = Collections.EMPTY_LIST;
        return classesCovered;
    }*/
}
