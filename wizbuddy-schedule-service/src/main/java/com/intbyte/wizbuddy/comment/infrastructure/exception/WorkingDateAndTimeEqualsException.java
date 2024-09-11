package com.intbyte.wizbuddy.comment.infrastructure.exception;


public class WorkingDateAndTimeEqualsException extends IllegalArgumentException {
    private final StatusEnum status;

    private static final String message = "이미 배정되어있는 직원입니다.";

    public WorkingDateAndTimeEqualsException() {
        super(message);
        this.status = StatusEnum.WORKINGDATE_AND_TIME_EQUALS;
    }
}
