package com.intbyte.wizbuddy.board.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestEditNoticeBoardDTO {
    private String noticeTitle;
    private String noticeContent;
    private String imageUrl;
    private LocalDateTime updatedAt;
    private String employerCode;
}
