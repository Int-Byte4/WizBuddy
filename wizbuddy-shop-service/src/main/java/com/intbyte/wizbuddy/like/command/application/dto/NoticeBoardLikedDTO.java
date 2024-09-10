package com.intbyte.wizbuddy.like.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeBoardLikedDTO {
    private int noticeLikedCode;
    private LocalDateTime createdAt;
    private int shopCode;
    private int noticeCode;
    private String employeeCode;
}
