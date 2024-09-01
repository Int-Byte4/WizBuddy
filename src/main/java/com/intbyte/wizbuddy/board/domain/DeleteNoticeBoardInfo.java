package com.intbyte.wizbuddy.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteNoticeBoardInfo {
    private int noticeCode;
    private boolean noticeFlag;
    private LocalDateTime updatedAt;
}
