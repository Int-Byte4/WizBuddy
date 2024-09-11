package com.intbyte.wizbuddy.comment.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {

    private int commentCode;

    private String commentContent;

    private boolean commentFlag;

    private boolean commentAdoptedState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int subsCode;

    private String employeeCode;
}
