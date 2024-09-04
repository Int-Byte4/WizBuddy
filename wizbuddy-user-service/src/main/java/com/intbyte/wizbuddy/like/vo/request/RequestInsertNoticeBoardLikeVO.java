package com.intbyte.wizbuddy.like.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestInsertNoticeBoardLikeVO {
    private LocalDateTime createdAt;
    private int shopCode;
    private int noticeCode;
    private String employeeCode;
}
