package com.intbyte.wizbuddy.board.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseUpdateNoticeBoardVO {
    private String noticeTitle;
    private String noticeContent;
    private boolean noticeFlag;
    private String imageUrl;
    private LocalDateTime updatedAt;
}