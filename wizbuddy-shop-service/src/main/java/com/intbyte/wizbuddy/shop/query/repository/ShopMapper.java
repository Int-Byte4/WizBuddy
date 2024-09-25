package com.intbyte.wizbuddy.shop.query.repository;

import com.intbyte.wizbuddy.shop.command.domain.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {
    String findByBusinessNum(String businessNum);

    Shop findShopByShopCode(int shopCode);

    int findByShopCode(int shopCode);

    List<String> getEmployerCode(String employerCode);
}
