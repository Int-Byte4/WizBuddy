package com.intbyte.wizbuddy.user.command.infrastructure.shop.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
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
