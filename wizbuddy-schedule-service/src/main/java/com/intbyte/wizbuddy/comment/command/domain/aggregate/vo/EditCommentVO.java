package com.intbyte.wizbuddy.comment.command.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentVO {
    private String commentContent;
    private boolean commentAdoptedState;
    private LocalDateTime updatedAt;
}
