package com.intbyte.wizbuddy.board.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ResponseUpdateManualBoardVO {
    private  String manualTitle;
    private  String manualContents;
    private  boolean manualFlag;
    private  String imageUrl;
    private  LocalDateTime updatedAt;
}
