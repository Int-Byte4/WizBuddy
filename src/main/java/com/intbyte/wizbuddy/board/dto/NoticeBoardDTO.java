package com.intbyte.wizbuddy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
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
