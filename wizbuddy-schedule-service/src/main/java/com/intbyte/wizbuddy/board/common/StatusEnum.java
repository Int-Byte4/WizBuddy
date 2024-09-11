package com.intbyte.wizbuddy.board.common;

import lombok.Getter;

@Getter
public enum StatusEnum {

    BOARD_NOT_FOUND(404, "BOARD_NOT_FOUND");

    private final int statusCode;
    private final String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
