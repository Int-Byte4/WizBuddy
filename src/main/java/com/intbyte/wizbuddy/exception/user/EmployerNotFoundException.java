package com.intbyte.wizbuddy.exception.user;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class EmployerNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "존재하지 않는 사용자입니다.";

    public EmployerNotFoundException() {
        super(message);
        this.status = StatusEnum.USER_NOT_FOUND;
    }
}
