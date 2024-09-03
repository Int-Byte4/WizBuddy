package com.intbyte.wizbuddy.board.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestUpdateManualBoardVO {
    private  String manualTitle;
    private  String manualContents;
    private  boolean manualFlag;
    private  String imageUrl;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  int shopCode;
    private  String userCode;
}
