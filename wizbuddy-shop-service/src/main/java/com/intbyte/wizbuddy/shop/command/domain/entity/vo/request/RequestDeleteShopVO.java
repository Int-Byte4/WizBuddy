package com.intbyte.wizbuddy.shop.command.domain.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestDeleteShopVO {
    private int shopCode;
    private String employerCode;
}
