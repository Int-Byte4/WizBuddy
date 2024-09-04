package com.intbyte.wizbuddy.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    EMAIL_DUPLICATE(403, "EMAIL_DUPLICATE"),
    EMPLOYEE_NOT_FOUND(404, "EMPLOYEE_NOT_FOUND"),
    EMPLOYER_NOT_FOUND(404, "EMPLOYER_NOT_FOUND");


    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
