package com.intbyte.wizbuddy.comment.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ResponseFindCommentVO {
    private int commentCode;
    private String commentContent;
    private boolean commentFlag;
    private boolean commentAdoptedState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int subsCode;
    private String employeeCode;
}
