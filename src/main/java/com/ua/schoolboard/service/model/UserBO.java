package com.ua.schoolboard.service.model;


import com.ua.schoolboard.persistence.model.GroupEntity;
import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Role;
import lombok.Data;

import java.util.*;

@Data
public class UserBO {
    private Long userId;
    private String name;
    private String surname;
    private String nickname;
    private String password;
    private Date birthDay;
    private String email;
    private String phoneNumber;
    private Role role;
    private Date joinedDate;
    private List<RatesBO> rates = new ArrayList<>();
    private Set<Language> languages = new HashSet<>();
    private BalanceBO balance;
    //private List<GroupBO> groups;
    private boolean active;
}
