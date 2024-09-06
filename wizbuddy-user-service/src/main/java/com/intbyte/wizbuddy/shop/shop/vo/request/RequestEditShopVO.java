package com.intbyte.wizbuddy.shop.shop.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestEditShopVO {
    private int shopCode;
    private String shopName;
    private String shopLocation;
    private LocalTime shopOpenTime;
    private LocalDateTime updatedAt;
    private String employerCode;
}
