package com.intbyte.wizbuddy.board.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ResponseFindManualBoardVO {
    private final int manualCode;
    private final String manualTitle;
    private final String manualContents;
    private final boolean manualFlag;
    private final String imageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int shopCode;
    private final String userCode;
}