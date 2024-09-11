package com.intbyte.wizbuddy.board.command.domain.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestUpdateNoticeBoardVO {
    private int noticeCode;
    private String noticeTitle;
    private String noticeContent;
    private boolean noticeFlag;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
    private String employerCode;
}