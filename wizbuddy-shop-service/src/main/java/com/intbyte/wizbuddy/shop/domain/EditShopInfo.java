package com.intbyte.wizbuddy.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditShopInfo {
    private int shopCode;
    private String shopName;
    private String shopLocation;
    private LocalTime shopOpenTime;
    private LocalDateTime updatedAt;
    private String employerCode;
}
