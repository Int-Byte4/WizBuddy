package com.intbyte.wizbuddy.weeklyschedule.common.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    SCHEDULE_CODE_DUPLICATE(403, "SCHEDULE_CODE_DUPLICATE");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
