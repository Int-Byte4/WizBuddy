package com.intbyte.wizbuddy.taskperchecklist.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPerCheckListMybatis {

    private Integer checkListCode;

    private Integer taskCode;

    private Boolean taskFinishedState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer employeeCode;
}
