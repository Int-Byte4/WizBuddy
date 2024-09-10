package com.intbyte.wizbuddy.shop.command.service;

import com.intbyte.wizbuddy.shop.command.application.dto.RequestDeleteShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestEditShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestRegisterShopDTO;
import com.intbyte.wizbuddy.shop.command.application.service.ShopService;
import com.intbyte.wizbuddy.shop.domain.ShopRepository;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopServiceTests {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Test
    @DisplayName("매장 등록 성공")
    @Transactional
    void testRegisterShopSuccess() {
        // given
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        List<Shop> currentShopList = shopRepository.findAll();
        RequestRegisterShopDTO shopInfo = RequestRegisterShopDTO.builder()
                .shopCode(currentShopList.size() + 1)
                .shopName("Test Shop")
                .shopLocation("Test Location")
                .shopFlag(true)
                .shopOpenTime(LocalTime.of(9, 0))
                .businessNum("999-99-99999")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        shopService.registerShop(employerCode, shopInfo);

        // then
        List<Shop> newShops = shopRepository.findAll();
        Shop shop = newShops.get(newShops.size() - 1);

        Assertions.assertThat(shop.getBusinessNum()).isEqualTo(shopInfo.getBusinessNum());
    }

    @Test
    @DisplayName("매장 정보 수정 성공")
    @Transactional
    void testUpdateShopSuccess() {
        //given
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        RequestEditShopDTO editShopInfo = RequestEditShopDTO.builder()
                .shopCode(1)
                .shopName("changeShopName")
                .shopLocation("changeShopLocation")
                .shopOpenTime(LocalTime.of(10, 0))
                .updatedAt(LocalDateTime.now())
                .employerCode(employerCode)
                .build();

        //when
        shopService.modifyShop(employerCode, editShopInfo);

        //then
        List<Shop> newShops = shopRepository.findAll();
        assertEquals(newShops.get(0).getShopName(), editShopInfo.getShopName());
    }

    @Test
    @DisplayName("매장 삭제 성공")
    @Transactional
    void testDeleteShopSuccess() {
        //given
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        RequestDeleteShopDTO deleteShopInfo = RequestDeleteShopDTO.builder()
                .shopCode(1)
                .employerCode(employerCode)
                .shopFlag(false)
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        shopService.deleteShop(employerCode, deleteShopInfo);

        //then
        List<Shop> newShops = shopRepository.findAll();
        assertEquals(false, newShops.get(deleteShopInfo.getShopCode() - 1).getShopFlag());
    }
}
