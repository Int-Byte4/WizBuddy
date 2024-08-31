package com.intbyte.wizbuddy.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentInfo {
    private String commentContent;
    private boolean commentAdoptedState;
    private LocalDateTime updatedAt;
}
