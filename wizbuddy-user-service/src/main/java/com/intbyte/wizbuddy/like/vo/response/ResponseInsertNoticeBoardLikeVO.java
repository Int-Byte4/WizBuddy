package com.intbyte.wizbuddy.like.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ResponseInsertNoticeBoardLikeVO {
    private LocalDateTime createdAt;
    private int shopCode;
    private int noticeCode;
    private String employeeCode;
}
