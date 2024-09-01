package com.intbyte.wizbuddy.exception.schedule;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class ScheduleCodeDuplicateException extends IllegalArgumentException{
    private final StatusEnum status;

    private static final String message = "이미 등록된 근무일정입니다.";

    public ScheduleCodeDuplicateException() {
        super(message);
        this.status = StatusEnum.SCHEDULE_CODE_DUPLICATE;
    }
}
