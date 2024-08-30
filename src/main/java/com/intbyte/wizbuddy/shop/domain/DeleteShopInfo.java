package com.intbyte.wizbuddy.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteShopInfo {
    private int shopCode;
    private int employerCode;
    private boolean shopFlag;
    private LocalDateTime updatedAt;
}
