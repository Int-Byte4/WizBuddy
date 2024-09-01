package com.intbyte.wizbuddy.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMybatis {

    private int taskCode;
    private String taskContents;
    private boolean taskFlag;
    private boolean taskFixedState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
}
