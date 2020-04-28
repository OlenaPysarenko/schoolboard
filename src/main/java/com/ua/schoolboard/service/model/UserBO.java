package com.ua.schoolboard.service.model;

import com.ua.schoolboard.persistence.Language;
import com.ua.schoolboard.persistence.Role;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserBO {
    private long userId;
    private String name;
    private String surname;
    private String nickname;
    private String password;
    private Date birthDay;
    private String email;
    private String phoneNumber;
    private Date joinedDate;
    private Set<Language> languages;
    private Role role;
    private int balance;
}
