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
    private String noticeContents;
    private boolean noticeFlag;
    private String imageURL;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
}
