package com.intbyte.wizbuddy.taskperchecklist.common;

import lombok.Getter;

@Getter
public enum StatusEnum {

    TASK_PER_CHECKLIST_NOT_FOUND(404, "TASK_PER_CHECKLIST_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}