package com.intbyte.wizbuddy.taskperchecklist.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class RequestModifyTaskPerCheckListVO {

//    private int checkListCode;
//    private int taskCode;
    private Boolean taskFinishedState;
//    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String employeeCode;
}
