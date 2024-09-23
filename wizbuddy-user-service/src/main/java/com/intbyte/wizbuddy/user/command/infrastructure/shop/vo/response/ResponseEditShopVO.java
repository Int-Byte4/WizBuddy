package com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.response;

import com.intbyte.wizbuddy.user.command.infrastructure.shop.dto.RequestEditShopDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ResponseEditShopVO {
    RequestEditShopDTO shopInfo;
}
