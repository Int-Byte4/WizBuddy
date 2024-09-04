package com.intbyte.wizbuddy.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteManualBoardInfo {
    private boolean manualFlag;
    private LocalDateTime updatedAt;
    private String userCode;
}