package com.intbyte.wizbuddy.shop.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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
    private int employerCode;
}
