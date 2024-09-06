package com.intbyte.wizbuddy.exception.user;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class UserNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "존재하지 않는 사용자입니다.";

    public UserNotFoundException() {
        super(message);
        this.status = StatusEnum.USER_NOT_FOUND;
    }
}