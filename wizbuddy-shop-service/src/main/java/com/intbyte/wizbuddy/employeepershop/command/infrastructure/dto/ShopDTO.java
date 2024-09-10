package com.intbyte.wizbuddy.employeepershop.command.infrastructure.dto;

import lombok.*;

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
