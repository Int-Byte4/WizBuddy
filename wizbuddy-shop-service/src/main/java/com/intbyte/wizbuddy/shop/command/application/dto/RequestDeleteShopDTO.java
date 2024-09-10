package com.intbyte.wizbuddy.shop.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RequestDeleteShopDTO {
    private int shopCode;
    private String employerCode;
    private boolean shopFlag;
    private LocalDateTime updatedAt;
}
