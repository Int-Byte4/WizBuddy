package com.intbyte.wizbuddy.comment.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestModifyCommentVO {
    private String commentContent;
    private LocalDateTime updatedAt;
}
