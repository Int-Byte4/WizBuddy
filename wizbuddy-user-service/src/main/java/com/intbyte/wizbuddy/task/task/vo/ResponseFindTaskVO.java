package com.intbyte.wizbuddy.task.task.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseFindTaskVO {
    private final int taskCode;
    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;
}
