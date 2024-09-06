package com.intbyte.wizbuddy.task.task.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestInsertTaskVO {
    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;
}
