package com.intbyte.wizbuddy.exception.shop;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class BusinessNumDuplicateException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "이미 등록된 매장입니다.";

    public BusinessNumDuplicateException() {
        super(message);
        this.status = StatusEnum.BUSINESS_NUM_DUPLICATE;
    }
}