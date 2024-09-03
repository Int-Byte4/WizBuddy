package com.intbyte.wizbuddy.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeBoardDTO {
    private int noticeCode;
    private String noticeTitle;
    private String noticeContent;
    private boolean noticeFlag;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;

}
