package com.ua.schoolboard.rest.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
//@EqualsAndHashCode(callSuper = true)
public class UpdateTeacherTO extends UserTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String nickname;
    private Date birthDay;
    private String phoneNumber;
    private Set<Language> languages;
    private BalanceTO balance;
    private List<TeacherRatesTO> rates;
    private boolean active;

     public void updateBalance(Integer sum) {
         BalanceTO balance = this.getBalance();
         if (balance == null) {
             balance = new BalanceTO();
         }
         balance.setAmount(balance.getAmount()+sum);
         this.setBalance(balance);
     }

}
