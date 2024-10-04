package com.intbyte.wizbuddy.shop.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RequestRegisterShopDTO {
    private String shopName;
    private String shopLocation;
    private boolean shopFlag;
    private LocalTime shopOpenTime;
    private String businessNum;
}
