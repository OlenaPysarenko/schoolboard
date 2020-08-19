package com.ua.schoolboard.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"languages", "balance", "rates"})
public class UpdateStudentTO extends UserTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String nickname;
    private String birthDay;
    private String phoneNumber;
    private Set<Language> languages = new HashSet<>();
    private BalanceTO balance;
    private List<RatesTO> rates = new ArrayList<>();
    private boolean active;

    //todo get rid of the workaround!!!
    public void updateBalance(Integer sum, Integer classesCovered) {
        BalanceTO balance = this.getBalance();
        if (balance == null) {
            balance = new BalanceTO();
        }
        balance.setAmount(balance.getAmount() + sum);
        balance.setClassesPaid(balance.getClassesPaid() + classesCovered);
        //balance.getPayments().add(balance.createPayment(this,sum));
    }

    public UpdateStudentTO() {
        this.balance = new BalanceTO();
        this.languages = new HashSet<>();
        this.rates = new ArrayList<>();
    }
}
