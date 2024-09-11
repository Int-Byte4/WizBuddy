package com.intbyte.wizbuddy.task.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskDTO {

    private int taskCode;
    private String taskContents;
    private boolean taskFlag;
    private boolean taskFixedState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
}
