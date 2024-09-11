package com.intbyte.wizbuddy.shop.domain.entity.vo.response;

import com.intbyte.wizbuddy.shop.command.application.dto.RequestEditShopDTO;
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
