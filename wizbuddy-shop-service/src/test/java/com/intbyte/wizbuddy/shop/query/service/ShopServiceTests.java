package com.intbyte.wizbuddy.shop.query.service;

import com.intbyte.wizbuddy.shop.query.dto.ShopDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopServiceTests {

    @Autowired
    private ShopService shopService;

    @Test
    @DisplayName("매장 전체 조회 성공")
    @Transactional
    void testGetShopListSuccess() {
        // given, when
        List<ShopDTO> shopList = shopService.getAllShop();

        // then
        assertNotNull(shopList);
        assertFalse(shopList.isEmpty());

        ShopDTO shopDTO = shopList.get(0);
        assertEquals("스타벅스 강남점", shopDTO.getShopName());
    }

    @Test
    @DisplayName("매장 단 건 조회 성공")
    @Transactional
    void testGetShopSuccess() {
        //given
        int shopCode = 1;

        //when
        ShopDTO shop = shopService.getShop(shopCode);


        //then
        assertNotNull(shop);
    }
}
