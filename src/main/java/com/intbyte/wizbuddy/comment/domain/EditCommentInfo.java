package com.intbyte.wizbuddy.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentInfo {
    private int commentCode;
    private String commentContent;
    private boolean commentFlag;
    private boolean commentAdoptedState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
