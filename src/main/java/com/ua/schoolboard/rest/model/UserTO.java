package com.ua.schoolboard.rest.model;

import com.ua.schoolboard.persistence.Role;
import lombok.Data;
@Data
public class UserTO {
    private long userId;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Role role;
}
