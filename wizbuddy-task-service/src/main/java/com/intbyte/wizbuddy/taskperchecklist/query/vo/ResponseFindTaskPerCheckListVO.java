package com.intbyte.wizbuddy.taskperchecklist.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Builder
public class ResponseFindTaskPerCheckListVO {
    private final int checkListCode;
    private final int taskCode;
    private final boolean taskFinishedState;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String employeeCode;
}
