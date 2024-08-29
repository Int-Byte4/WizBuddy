package com.intbyte.wizbuddy.shop.service;

import com.intbyte.wizbuddy.exception.shop.BusinessNumDuplicateException;
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
}
