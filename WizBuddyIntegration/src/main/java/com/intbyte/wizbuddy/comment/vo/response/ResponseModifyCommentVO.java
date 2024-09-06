package com.intbyte.wizbuddy.comment.vo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseModifyCommentVO {
    private String commentContent;
    private LocalDateTime updatedAt;
}
