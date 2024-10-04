package com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseInsertCommentVO {
    private String commentContent;
    private boolean commentFlag;
    private boolean commentAdoptedState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int subsCode;
    private String employeeCode;
    private int shopCode;
}
