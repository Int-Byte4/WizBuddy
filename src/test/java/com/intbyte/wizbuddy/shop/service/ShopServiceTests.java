package com.intbyte.wizbuddy.shop.service;

import com.intbyte.wizbuddy.exception.shop.BusinessNumDuplicateException;
import com.intbyte.wizbuddy.shop.domain.DeleteShopInfo;
import com.intbyte.wizbuddy.shop.domain.EditShopInfo;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
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
        int employerCode = 1;
        List<Shop> currentShopList = shopRepository.findAll();
        ShopDTO shopDTO = new ShopDTO(currentShopList.size() + 1, "Test Shop", "Test Location", true, LocalTime.of(9, 0), "999-99-99999", LocalDateTime.now(), LocalDateTime.now(), employerCode);

        // when
        shopService.registerShop(shopDTO);

        // then
        List<Shop> newShops = shopRepository.findAll();
        Shop shop = newShops.get(newShops.size() - 1);

        newShops.forEach(System.out::println);

        Assertions.assertThat(shop.getBusinessNum()).isEqualTo(shopDTO.getBusinessNum());
        newShops.forEach(System.out::println);
    }

    @Test
    @DisplayName("사업자 번호 중복으로 매장 등록 실패")
    @Transactional
    void testRegisterShopThrowsBusinessNumDuplicateException() {
        // given
        int employerCode = 1;
        List<Shop> currentShopList = shopRepository.findAll();
        ShopDTO shopDTO = new ShopDTO(currentShopList.size() + 1, "Test Shop", "Test Location", true, LocalTime.of(9, 0), "123-45-67890", LocalDateTime.now(), LocalDateTime.now(), employerCode);

        // when & then
        assertThrows(BusinessNumDuplicateException.class, () -> shopService.registerShop(shopDTO));
    }

    @Test
    @DisplayName("매장 정보 수정 성공")
    @Transactional
    void testUpdateShopSuccess() {
        //given
        int employerCode = 1;
        List<Shop> currentShopList = shopRepository.findAll();
        Shop shop = currentShopList.get(0);
        EditShopInfo editShopInfo = new EditShopInfo("changeShopName", "changeShopLocation", LocalTime.of(10, 0), LocalDateTime.now());

        //when
        shopService.modifyShop(employerCode, shop.getShopCode(), editShopInfo);

        //then
        List<Shop> newShops = shopRepository.findAll();
        assertEquals(newShops.get(0).getShopName(), editShopInfo.getShopName());

        newShops.forEach(System.out::println);
    }

    @Test
    @DisplayName("매장 삭제 성공")
    @Transactional
    void testDeleteShopSuccess() {
        //given
        DeleteShopInfo deleteShopInfo = new DeleteShopInfo(1, 1, false);

        //when
        shopService.deleteShop(deleteShopInfo);

        //then
        List<Shop> newShops = shopRepository.findAll();
        assertEquals(false, newShops.get(deleteShopInfo.getShopCode() - 1).getShopFlag());

        newShops.forEach(System.out::println);
    }
}
