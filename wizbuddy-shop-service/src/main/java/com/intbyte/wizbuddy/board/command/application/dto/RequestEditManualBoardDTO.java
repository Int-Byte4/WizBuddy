package com.intbyte.wizbuddy.board.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RequestEditManualBoardDTO {
    private String manualTitle;
    private String manualContents;
    private String imageUrl;
    private LocalDateTime updatedAt;
    private String userCode;
}
