package com.intbyte.wizbuddy.user.command.infrastructure.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@Builder
public class RequestEditShopDTO {
    private int shopCode;
    private String shopName;
    private String shopLocation;
    private LocalTime shopOpenTime;
    private LocalDateTime updatedAt;
    private String employerCode;
}