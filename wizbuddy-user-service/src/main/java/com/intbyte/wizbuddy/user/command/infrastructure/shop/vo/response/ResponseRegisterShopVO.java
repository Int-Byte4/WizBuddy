package com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.response;

import com.intbyte.wizbuddy.user.command.infrastructure.shop.dto.RequestRegisterShopDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterShopVO {
    RequestRegisterShopDTO shopInfo;
}