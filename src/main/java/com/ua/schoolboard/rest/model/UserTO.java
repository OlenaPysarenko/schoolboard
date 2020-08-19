package com.ua.schoolboard.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class UserTO {
    private Long userId;
    private String password;
    private String email;
    private Role role;
    private Date joinedDate;
    private boolean active;

}
