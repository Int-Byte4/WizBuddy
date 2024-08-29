package com.intbyte.wizbuddy.shop.service;

import com.intbyte.wizbuddy.exception.shop.BusinessNumDuplicateException;
import com.intbyte.wizbuddy.mapper.ShopMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final EmployerRepository employerRepository;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Transactional
    public void registerShop(ShopDTO shopInfo) {
//        employerRepository.findById(employerCode).orElseThrow(EmployerNotFoundException::new);

        String shopName = shopInfo.getShopName();
        String shopLocation = shopInfo.getShopLocation();
        LocalTime shopOpenTime = shopInfo.getShopOpenTime();
        String businessNum = shopInfo.getBusinessNum();
        int employerCode = shopInfo.getEmployerCode();

        if (shopMapper.findByBusinessNum(businessNum) != null) throw new BusinessNumDuplicateException();

        Shop shop = Shop.builder()
                .shopName(shopName)
                .shopLocation(shopLocation)
                .shopFlag(true)
                .shopOpenTime(shopOpenTime)
                .businessNum(businessNum)
                .employerCode(employerCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        shopRepository.save(shop);
    }
}
