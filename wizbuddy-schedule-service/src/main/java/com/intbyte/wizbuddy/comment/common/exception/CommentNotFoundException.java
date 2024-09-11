package com.intbyte.wizbuddy.comment.common.exception;


import com.intbyte.wizbuddy.comment.common.StatusEnum;

public class CommentNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 댓글이 존재하지 않습니다.";

    public CommentNotFoundException() {
        super(message);
        this.status = StatusEnum.COMMENT_NOT_FOUND;
    }
}
