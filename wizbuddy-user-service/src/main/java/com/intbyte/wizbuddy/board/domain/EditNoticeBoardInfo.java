package com.intbyte.wizbuddy.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditNoticeBoardInfo {
    private int noticeCode;
    private String noticeTitle;
    private String noticeContent;
    private String imageUrl;
    private boolean noticeFlag;
    private LocalDateTime updatedAt;
    private String employerCode;
}