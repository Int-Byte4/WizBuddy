package com.intbyte.wizbuddy.board.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseInsertManualBoardVO {
    private  String manualTitle;
    private  String manualContents;
    private  boolean manualFlag;
    private  String imageUrl;
    private  LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  int shopCode;
    private  String userCode;
}