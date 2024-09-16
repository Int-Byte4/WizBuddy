package com.intbyte.wizbuddy.board.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestEditManualBoardDTO {
    private String manualTitle;
    private String manualContents;
    private String imageUrl;
    private LocalDateTime updatedAt;
    private String userCode;
}
