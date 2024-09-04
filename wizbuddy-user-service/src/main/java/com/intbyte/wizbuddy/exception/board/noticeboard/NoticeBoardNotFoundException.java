package com.intbyte.wizbuddy.exception.board.noticeboard;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class NoticeBoardNotFoundException extends IllegalArgumentException{
    private final StatusEnum status;

    private static final String message = "게시글이 더 이상 존재하지 않습니다.";

    public NoticeBoardNotFoundException() {
        super(message);
        this.status = StatusEnum.BOARD_NOT_FOUND;
    }
}
