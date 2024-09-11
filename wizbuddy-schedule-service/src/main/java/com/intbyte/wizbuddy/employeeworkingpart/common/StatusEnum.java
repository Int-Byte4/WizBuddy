package com.intbyte.wizbuddy.employeeworkingpart.common;

import lombok.Getter;

@Getter
public enum StatusEnum {

    SCHEDULE_NOT_FOUND(404, "SCHEDULE_NOT_FOUND"),
    EMPLOYEE_CODE_NOT_FOUND(404, "EMPLOYEE_CODE_NOT_FOUND"),
    WORKINGDATE_AND_TIME_EQUALS(409, "WORKINGDATE_AND_TIME_EQUALS");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
