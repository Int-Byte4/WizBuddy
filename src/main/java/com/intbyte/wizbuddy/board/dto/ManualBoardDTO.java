package com.intbyte.wizbuddy.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ManualBoardDTO {
    private int manualCode;
    private String manualTitle;
    private String manualContents;
    private boolean manualFlag;
    private String imageURL;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
}
