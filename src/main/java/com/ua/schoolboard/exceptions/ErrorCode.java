package com.ua.schoolboard.exceptions;

public enum ErrorCode {
    USER_NOT_FOUND("Sorry, there is no such user!"),
    USER_ALREADY_EXISTS("This user has been registered previously.Pick another email/password"),
    INVALID_CREDENTIAL("Username or password is incorrect, try again"),
    RATES_ALREADY_EXIST(""),
    RATES_NOT_FOUND(""),
    GROUP_ALREADY_EXISTS(""),
    GROUP_NOT_FOUND(""),
    CLASS_ALREADY_EXISTS(""),
    CLASS_NOT_FOUND("");


    ErrorCode(String s) {
    }
}
