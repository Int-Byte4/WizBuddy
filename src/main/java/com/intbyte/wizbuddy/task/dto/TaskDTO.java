package com.intbyte.wizbuddy.task.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskDTO {

    private int TaskCode;

    private String TaskContents;

    private boolean TaskFlag;

    private boolean TaskFixedState;

    private LocalDateTime CreatedAt;

    private LocalDateTime UpdatedAt;
}
