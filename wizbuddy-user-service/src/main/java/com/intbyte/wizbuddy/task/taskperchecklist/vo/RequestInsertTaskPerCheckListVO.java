package com.intbyte.wizbuddy.task.taskperchecklist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestInsertTaskPerCheckListVO {

    private final Boolean taskFinishedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String employeeCode;
}
