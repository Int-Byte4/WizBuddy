package com.intbyte.wizbuddy.board.query.dto;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ManualBoardDTO {
    private int manualCode;
    private String manualTitle;
    private String manualContents;
    private boolean manualFlag;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int shopCode;
    private String userCode;
}