package com.intbyte.wizbuddy.shop.command.domain.entity.vo.response;

import com.intbyte.wizbuddy.shop.command.application.dto.RequestRegisterShopDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterShopVO {
    RequestRegisterShopDTO shopInfo;
}
