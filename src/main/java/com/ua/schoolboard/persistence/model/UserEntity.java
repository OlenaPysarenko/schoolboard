package com.ua.schoolboard.persistence.model;


import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Role;
import lombok.Data;


import javax.persistence.*;
import java.util.*;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private Date birthDay;

    @Column(nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private Date joinedDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_lang", joinColumns = @JoinColumn(name = "user_id"),foreignKey = @ForeignKey(name = "fk_user_lang"))
    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Set<Language> languages = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<RatesEntity> rates = new ArrayList<>();

    @Column
    private boolean active;

   @Embedded
    private BalanceEntity balance;
   

}
