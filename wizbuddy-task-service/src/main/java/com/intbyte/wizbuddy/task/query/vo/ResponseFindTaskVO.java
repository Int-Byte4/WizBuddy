package com.intbyte.wizbuddy.task.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Builder
public class ResponseFindTaskVO {

    private final int taskCode;
    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;
}
