package com.intbyte.wizbuddy.exception.manualboard;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class ManualBoardModifyOtherUserException extends IllegalArgumentException {
    private final StatusEnum status;

    private static final String message = "작성자와 수정 요청한 유저가 일치하지 않습니다.";

    public ManualBoardModifyOtherUserException() {
        super(message);
        this.status = StatusEnum.RESTRICTED;
    }
}
