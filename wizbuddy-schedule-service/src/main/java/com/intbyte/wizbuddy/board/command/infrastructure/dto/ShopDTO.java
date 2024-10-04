package com.intbyte.wizbuddy.board.command.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@Builder
public class ShopDTO {
    private int shopCode;
    private String shopName;
    private String shopLocation;
    private Boolean shopFlag;
    private LocalTime shopOpenTime;
    private String businessNum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String employerCode;
}
