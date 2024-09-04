package com.intbyte.wizbuddy.shop.vo.response;

import com.intbyte.wizbuddy.shop.domain.EditShopInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ResponseEditShopVO {
    EditShopInfo shopInfo;
}
