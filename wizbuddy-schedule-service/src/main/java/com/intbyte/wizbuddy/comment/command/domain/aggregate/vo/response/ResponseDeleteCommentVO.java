package com.intbyte.wizbuddy.comment.command.domain.aggregate.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseDeleteCommentVO {
    private boolean commentFlag;
    private LocalDateTime updatedAt;
}
