package com.intbyte.wizbuddy.comment.common;

import lombok.Getter;

@Getter
public enum StatusEnum {

    COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
