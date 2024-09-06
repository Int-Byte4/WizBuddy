package com.intbyte.wizbuddy.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    TASK_NOT_FOUND(404, "TASK_NOT_FOUND"),
    CHECK_LIST_NOT_FOUND(404, "CHECK_LIST_NOT_FOUND"),
    TASK_PER_CHECKLIST_NOT_FOUND(404, "TASK_PER_CHECKLIST_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
