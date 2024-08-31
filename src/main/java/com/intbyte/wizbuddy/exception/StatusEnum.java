package com.intbyte.wizbuddy.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    USER_NOT_FOUND(404,"USER_NOT_FOUND"),
    EMAIL_DUPLICATE(403, "EMAIL_DUPLICATE"),
    SHOP_NOT_FOUND(404, "SHOP_NOT_FOUND"),
    BUSINESS_NUM_DUPLICATE(403, "BUSINESS_NUM_DUPLICATE"),
    RESTRICTED(403, "RESTRICTED"),
    BOARD_NOT_FOUND(404, "BOARD_NOT_FOUND"),
    TASK_NOT_FOUND(404, "TASK_NOT_FOUND"),
    CHECK_LIST_NOT_FOUND(404, "CHECK_LIST_NOT_FOUND"),
    SCHEDULE_NOT_FOUND(404, "SCHEDULE_NOT_FOUND"),
    EMPLOYEE_NOT_FOUND(404, "EMPLOYEE_NOT_FOUND"),
    SCHEDULE_CODE_DUPLICATE(403, "SCHEDULE_CODE_DUPLICATE" );

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
