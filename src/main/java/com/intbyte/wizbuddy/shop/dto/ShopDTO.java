package com.intbyte.wizbuddy.shop.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShopDTO {
    public int shopCode;
    public String shopName;
    public String shopLocation;
    public Boolean shopFlag;
    public LocalTime shopOpenTime;
    public String businessNum;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public Integer employerCode;
}
