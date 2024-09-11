package com.intbyte.wizbuddy.comment.infrastructure.exception;

public class WorkingPartCodeNotEqualsException extends IllegalArgumentException {
    private final StatusEnum status;

    private static final String message = "근무파트가 일치하지 않는 직원입니다.";

    public WorkingPartCodeNotEqualsException() {
        super(message);
        this.status = StatusEnum.WORKINGPART_NOT_EQUALS;
    }
}
