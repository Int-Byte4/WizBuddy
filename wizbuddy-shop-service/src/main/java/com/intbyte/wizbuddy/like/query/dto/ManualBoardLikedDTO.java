package com.intbyte.wizbuddy.like.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ManualBoardLikedDTO {
    private int manualLikedCode;
//    private LocalDateTime createdAt;
//    private int manualCode;
//    private int shopCode;
    private String employeeCode;
}
