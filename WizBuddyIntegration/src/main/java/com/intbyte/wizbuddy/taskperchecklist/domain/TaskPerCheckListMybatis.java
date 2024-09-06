package com.intbyte.wizbuddy.taskperchecklist.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPerCheckListMybatis {

    private Integer checkListCode;

    private Integer taskCode;

    private Boolean taskFinishedState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String employeeCode;
}
