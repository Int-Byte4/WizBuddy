package com.intbyte.wizbuddy.like.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseInsertNoticeBoardLikeVO {
    private LocalDateTime createdAt;
    private int shopCode;
    private int noticeCode;
    private String employeeCode;
}
