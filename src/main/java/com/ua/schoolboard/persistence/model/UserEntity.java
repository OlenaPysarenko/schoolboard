package com.ua.schoolboard.persistence.model;

import com.ua.schoolboard.persistence.Language;
import com.ua.schoolboard.persistence.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private Date birthDay;

    @Column(nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private Date joinedDate;

   /*@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Language> languages;
*/
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private int balance;//salary for teachers, balance for students

}
