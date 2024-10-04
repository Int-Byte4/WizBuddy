package com.intbyte.wizbuddy.board.command.domain.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ResponseUpdateNoticeBoardVO {
    private String noticeTitle;
    private String noticeContent;
    private boolean noticeFlag;
    private String imageUrl;
    private LocalDateTime updatedAt;
}