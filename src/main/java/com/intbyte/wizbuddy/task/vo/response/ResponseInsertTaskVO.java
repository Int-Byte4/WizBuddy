package com.intbyte.wizbuddy.task.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ResponseInsertTaskVO {

    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;
}
