package com.intbyte.wizbuddy.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditManualBoardInfo {
    private int manualCode;
    private String manualTitle;
    private String manualContents;
    private String imageUrl;
    private boolean manualFlag;
    private LocalDateTime updatedAt;
    private String userCode;
}
