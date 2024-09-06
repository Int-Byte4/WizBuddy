package com.intbyte.wizbuddy.like.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ResponseInsertManualBoardLikeVO {
    private LocalDateTime createdAt;
    private int manualCode;
    private int shopCode;
    private String employeeCode;
}
