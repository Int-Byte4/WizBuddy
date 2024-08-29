package com.intbyte.wizbuddy.shop.service;

import com.intbyte.wizbuddy.exception.shop.BusinessNumDuplicateException;
import com.intbyte.wizbuddy.mapper.ShopMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

        String businessNum = shopInfo.getBusinessNum();

        if (shopMapper.findByBusinessNum(businessNum) != null) throw new BusinessNumDuplicateException();

        Shop shop = Shop.builder()
                .shopName(shopInfo.getShopName())
                .shopLocation(shopInfo.getShopLocation())
                .shopFlag(true)
                .shopOpenTime(shopInfo.getShopOpenTime())
                .businessNum(businessNum)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .employerCode(shopInfo.getEmployerCode())
                .build();
        
        shopRepository.save(shop);
    }
}
