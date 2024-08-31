package com.intbyte.wizbuddy.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditManualBoardInfo {
    private int manualBoardCode;
    private String manualTitle;
    private String manualContents;
    private String imageUrl;
    private LocalDateTime updatedAt;
    private int userCode;
}
