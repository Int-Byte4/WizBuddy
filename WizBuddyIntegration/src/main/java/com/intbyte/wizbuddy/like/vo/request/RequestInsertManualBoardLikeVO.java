package com.intbyte.wizbuddy.like.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestInsertManualBoardLikeVO {
    private LocalDateTime createdAt;
    private int manualCode;
    private int shopCode;
    private String employeeCode;
}
