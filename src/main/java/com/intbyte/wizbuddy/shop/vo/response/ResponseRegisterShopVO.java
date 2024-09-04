package com.intbyte.wizbuddy.shop.vo.response;

import com.intbyte.wizbuddy.shop.domain.RegisterShopInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseRegisterShopVO {
    RegisterShopInfo shopInfo;
}
