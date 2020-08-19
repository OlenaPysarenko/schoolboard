package com.ua.schoolboard.persistence;

import javax.persistence.Embeddable;
import java.util.Date;
@Embeddable
public class Hometask {
    Date dueDate;//make it clear!
    private String task;
}
