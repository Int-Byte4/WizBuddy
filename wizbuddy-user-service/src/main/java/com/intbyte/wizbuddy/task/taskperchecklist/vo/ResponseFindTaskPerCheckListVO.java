package com.intbyte.wizbuddy.task.taskperchecklist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseFindTaskPerCheckListVO {

    private final int checkListCode;
    private final int taskCode;
    private final boolean taskFinishedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String employeeCode;
}
