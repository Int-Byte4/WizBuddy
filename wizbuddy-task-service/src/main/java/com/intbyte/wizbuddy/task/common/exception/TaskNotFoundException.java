package com.intbyte.wizbuddy.task.common.exception;


import com.intbyte.wizbuddy.task.common.StatusEnum;

public class TaskNotFoundException extends IllegalArgumentException{

    private final StatusEnum status;

    private static final String message = "해당 업무가 존재하지 않습니다.";

    public TaskNotFoundException() {
        super(message);
        this.status = StatusEnum.TASK_NOT_FOUND;
    }
}
