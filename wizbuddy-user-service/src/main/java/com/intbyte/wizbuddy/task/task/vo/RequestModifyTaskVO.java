package com.intbyte.wizbuddy.task.task.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestModifyTaskVO {
    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime updatedAt;
}
