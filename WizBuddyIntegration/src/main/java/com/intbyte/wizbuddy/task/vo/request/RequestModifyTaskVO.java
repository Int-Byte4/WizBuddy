package com.intbyte.wizbuddy.task.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class RequestModifyTaskVO {

    private final String taskContents;
    private final boolean taskFlag;
    private final boolean taskFixedState;
    private final LocalDateTime updatedAt;
}
