package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopMapper {
    String findByBusinessNum(String businessNum);

    Shop findShopByShopCode(int shopCode);
}
