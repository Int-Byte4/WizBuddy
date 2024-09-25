package com.intbyte.wizbuddy.shop.command.domain.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRegisterShopVO {
    private String shopName;
    private String shopLocation;
    private boolean shopFlag;
    private LocalTime shopOpenTime;
    private String businessNum;
}
