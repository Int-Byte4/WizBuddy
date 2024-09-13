package com.intbyte.wizbuddy.board.command.domain.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseUpdateManualBoardVO {
    private  String manualTitle;
    private  String manualContents;
    private  boolean manualFlag;
    private  String imageUrl;
    private  LocalDateTime updatedAt;
}