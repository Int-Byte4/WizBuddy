package com.intbyte.wizbuddy.exception.board;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class LikeDuplicateException  extends IllegalArgumentException {
    private final StatusEnum status;

    private static final String message = "이미 좋아요 누른 게시글입니다.";

    public LikeDuplicateException() {
        super(message);
        this.status = StatusEnum.ALREADY_PUSH_LIKED;
    }
}
