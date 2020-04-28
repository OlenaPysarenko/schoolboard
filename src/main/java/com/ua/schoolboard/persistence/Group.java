package com.ua.schoolboard.persistence;

import java.util.List;

public class Group {
    private int groupId;
    private String groupName;//groupName or put "Indiv"
    private String lvl;
    private String bookName;
    Duration duration;
   // Teacher teacher;
   // List<Student> students;
    private int classesCovered;//if >60, warn teacher
}
