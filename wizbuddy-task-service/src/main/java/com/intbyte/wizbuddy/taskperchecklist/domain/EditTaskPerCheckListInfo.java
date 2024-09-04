package com.intbyte.wizbuddy.taskperchecklist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditTaskPerCheckListInfo {

    private Integer checkListCode;

    private Integer taskCode;

    private Boolean taskFinishedState;

    private LocalDateTime updatedAt;

    private String employeeCode;
}
