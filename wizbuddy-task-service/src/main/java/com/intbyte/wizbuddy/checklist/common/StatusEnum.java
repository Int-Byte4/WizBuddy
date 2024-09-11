package com.intbyte.wizbuddy.checklist.common;

import lombok.Getter;

@Getter
public enum StatusEnum {

    CHECK_LIST_NOT_FOUND(404, "CHECK_LIST_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
