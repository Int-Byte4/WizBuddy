package com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseAdoptCommentVO {
    private boolean commentAdoptedState;
    private LocalDateTime updatedAt;
    private int subsCode;
    private String employeeCode;
}
