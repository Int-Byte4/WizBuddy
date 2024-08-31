package com.intbyte.wizbuddy.comment.dto;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {

    private int commentCode;

    private String comment_contents;

    private boolean comment_flag;

    private boolean comment_adopted_state;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private int subsCode;

    private int employeeCode;
}
