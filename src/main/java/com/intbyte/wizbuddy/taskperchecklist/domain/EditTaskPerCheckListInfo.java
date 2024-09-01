package com.intbyte.wizbuddy.taskperchecklist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditTaskPerCheckListInfo {

//    private Integer checkListCode;

//    private Integer taskCode;

    private Boolean taskFinishedState;

//    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // private Integer employeeCode;
    private String employeeCode;
}
