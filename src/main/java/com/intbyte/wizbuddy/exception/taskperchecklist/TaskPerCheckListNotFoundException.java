package com.intbyte.wizbuddy.exception.taskperchecklist;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class TaskPerCheckListNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 업무별 체크리스트가 존재하지 않습니다.";

    public TaskPerCheckListNotFoundException() {
        super(message);
        this.status = StatusEnum.TASK_PER_CHECKLIST_NOT_FOUND;
    }
}
