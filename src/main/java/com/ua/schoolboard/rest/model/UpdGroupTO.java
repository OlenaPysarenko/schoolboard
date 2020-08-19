package com.ua.schoolboard.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@EqualsAndHashCode(callSuper = true)
public class UpdGroupTO extends GroupTO{
    private String groupName;
    private GroupType groupType;
    private Language language;
    private Levels lvl;
    private String bookName;
}
