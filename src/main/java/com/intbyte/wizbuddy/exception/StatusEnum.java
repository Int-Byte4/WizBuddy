package com.intbyte.wizbuddy.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    USER_NOT_FOUND(404,"USER_NOT_FOUND"),
    EMAIL_DUPLICATE(403, "EMAIL_DUPLICATE"),
    SHOP_NOT_FOUND(404, "SHOP_NOT_FOUND"),
    BUSINESS_NUM_DUPLICATE(403, "BUSINESS_NUM_DUPLICATE"),
    RESTRICTED(403, "RESTRICTED"),
    TASK_NOT_FOUND(404, "TASK_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
