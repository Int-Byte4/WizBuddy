package com.intbyte.wizbuddy.shop.service;

import com.intbyte.wizbuddy.exception.shop.BusinessNumDuplicateException;
import com.intbyte.wizbuddy.shop.domain.DeleteShopInfo;
import com.intbyte.wizbuddy.shop.domain.EditShopInfo;
import com.intbyte.wizbuddy.shop.domain.RegisterShopInfo;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        RegisterShopInfo shopInfo = new RegisterShopInfo(currentShopList.size() + 1, "Test Shop", "Test Location", true, LocalTime.of(9, 0), "999-99-99999", LocalDateTime.now(), LocalDateTime.now());

        // when
        shopService.registerShop(employerCode, shopInfo);

        // then
        List<Shop> newShops = shopRepository.findAll();
        Shop shop = newShops.get(newShops.size() - 1);

        Assertions.assertThat(shop.getBusinessNum()).isEqualTo(shopInfo.getBusinessNum());
    }

    @Test
    @DisplayName("사업자 번호 중복으로 매장 등록 실패")
    @Transactional
    void testRegisterShopThrowsBusinessNumDuplicateException() {
        // given
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        List<Shop> currentShopList = shopRepository.findAll();
        RegisterShopInfo shopInfo = new RegisterShopInfo(currentShopList.size() + 1, "Test Shop", "Test Location", true, LocalTime.of(9, 0), "123-45-67890", LocalDateTime.now(), LocalDateTime.now());

        // when & then
        assertThrows(BusinessNumDuplicateException.class, () -> shopService.registerShop(employerCode, shopInfo));
    }

    @Test
    @DisplayName("매장 정보 수정 성공")
    @Transactional
    void testUpdateShopSuccess() {
        //given
        String employerCode = "20240831-3750-4218-9aed-7eabc7c634c2";
        EditShopInfo editShopInfo = new EditShopInfo(1, "changeShopName", "changeShopLocation", LocalTime.of(10, 0), LocalDateTime.now(), employerCode);

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
        DeleteShopInfo deleteShopInfo = new DeleteShopInfo(1, employerCode, false, LocalDateTime.now());

        //when
        shopService.deleteShop(employerCode, deleteShopInfo);

        //then
        List<Shop> newShops = shopRepository.findAll();
        assertEquals(false, newShops.get(deleteShopInfo.getShopCode() - 1).getShopFlag());
    }

    @Test
    @DisplayName("매장 전체 조회 성공")
    @Transactional
    void testGetShopListSuccess() {
        // given
        String userCode = "20240831-07de-4b18-95c6-564cd86a5af2";

        // when
        List<ShopDTO> shopList = shopService.getAllShop(userCode);

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
        String userCode = "20240831-5e1c-400a-8f17-df561d451480";
        int shopCode = 1;

        //when
        Shop shop = shopService.getShop(userCode, shopCode);

        //then
        assertNotNull(shop);
    }
}
