package com.ua.schoolboard.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1,"Sorry, there is no such user!"),
    USER_ALREADY_EXISTS(2, "This user has been registered previously.Pick another email/password"),
    INVALID_CREDENTIALS(3, "Username or password is incorrect, try again"),
    RATES_ALREADY_EXIST(4, ""),
    RATES_NOT_FOUND(5, ""),
    GROUP_ALREADY_EXISTS(6, ""),
    GROUP_NOT_FOUND(7, ""),
    CLASS_ALREADY_EXISTS(7, ""),
    CLASS_NOT_FOUND(8, "");

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
    }
}
