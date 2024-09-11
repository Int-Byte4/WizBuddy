package com.intbyte.wizbuddy.board.command.domain.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestInsertManualBoardVO {
    private  String manualTitle;
    private  String manualContents;
    private  boolean manualFlag;
    private  String imageUrl;
    private  LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  int shopCode;
    private  String userCode;
}