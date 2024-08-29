package com.intbyte.wizbuddy.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditTaskInfo {
//    private int taskCode;
    private String taskContents;
    private boolean taskFlag;
    private boolean taskFixedState;
//    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
