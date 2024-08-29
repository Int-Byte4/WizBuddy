package com.intbyte.wizbuddy.exception.user;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class EmailDuplicateException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "이미 등록된 이메일입니다.";

    public EmailDuplicateException() {
        super(message);
        this.status = StatusEnum.EMAIL_DUPLICATE;
    }
}
