package com.intbyte.wizbuddy.task.taskperchecklist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestModifyTaskPerCheckListVO {

    private final Boolean taskFinishedState;
    private final LocalDateTime updatedAt;
    private final String employeeCode;
}
