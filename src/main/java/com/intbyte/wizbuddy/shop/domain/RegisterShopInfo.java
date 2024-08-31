package com.intbyte.wizbuddy.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterShopInfo {
    private int shopCode;
    private String shopName;
    private String shopLocation;
    private boolean shopFlag;
    private LocalTime shopOpenTime;
    private String businessNum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
