package com.intbyte.wizbuddy.shop.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RequestDeleteShopDTO {
    private int shopCode;
    private String employerCode;
}
