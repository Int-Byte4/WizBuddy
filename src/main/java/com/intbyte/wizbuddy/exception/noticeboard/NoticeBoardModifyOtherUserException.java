package com.intbyte.wizbuddy.exception.noticeboard;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class NoticeBoardModifyOtherUserException extends IllegalArgumentException {
    private final StatusEnum status;

    private static final String message = "작성자와 수정 요청한 유저가 일치하지 않습니다.";

    public NoticeBoardModifyOtherUserException() {
        super(message);
        this.status = StatusEnum.RESTRICTED;
    }
}
