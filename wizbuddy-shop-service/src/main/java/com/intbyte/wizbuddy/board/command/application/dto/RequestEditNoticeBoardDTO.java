package com.intbyte.wizbuddy.board.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RequestEditNoticeBoardDTO {
    private String noticeTitle;
    private String noticeContent;
    private String imageUrl;
    private LocalDateTime updatedAt;
}
