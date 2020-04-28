package com.ua.schoolboard.rest.model;

import com.ua.schoolboard.persistence.Language;

import java.util.Date;
import java.util.Set;


public class ParticipantTO extends UserTO {
    private String phoneNumber;
    private String nickname;
    private Date birthDay;
    private Date joinedDate;
    private Set<Language> languages;
    private int balance;
}
