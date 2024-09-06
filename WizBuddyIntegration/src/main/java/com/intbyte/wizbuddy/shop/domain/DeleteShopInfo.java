package com.intbyte.wizbuddy.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteShopInfo {
    private int shopCode;
    private String employerCode;
    private boolean shopFlag;
    private LocalDateTime updatedAt;
}
