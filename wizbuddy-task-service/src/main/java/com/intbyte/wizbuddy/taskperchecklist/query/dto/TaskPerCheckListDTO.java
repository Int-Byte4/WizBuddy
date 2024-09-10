package com.intbyte.wizbuddy.taskperchecklist.query.dto;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskPerCheckListDTO {
    private int checkListCode;

    private int taskCode;

    private Boolean taskFinishedState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String employeeCode;
}
