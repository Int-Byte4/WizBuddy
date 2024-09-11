package com.intbyte.wizbuddy.shop.query.service;

import com.intbyte.wizbuddy.employeepershop.common.exception.CommonException;
import com.intbyte.wizbuddy.employeepershop.common.exception.StatusEnum;
import com.intbyte.wizbuddy.shop.query.repository.ShopMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.query.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("shopQueryService")
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ShopDTO> getAllShop() {
        List<ShopDTO> shopDTOList = new ArrayList<>();

        for (Shop shop : shopRepository.findAll()) {
            ShopDTO shopDTO = modelMapper.map(shop, ShopDTO.class);

            shopDTOList.add(shopDTO);
        }

        return shopDTOList;
    }

    @Transactional
    public ShopDTO getShop(int shopCode) {
        Shop shop = shopMapper.findShopByShopCode(shopCode);

        ShopDTO shopDTO = convertToShopDTO(shop);

        if (shopDTO == null) throw new CommonException(StatusEnum.SHOP_NOT_FOUND);

        return shopDTO;
    }

    private ShopDTO convertToShopDTO(Shop shop) {
        return ShopDTO.builder()
                .shopCode(shop.getShopCode())
                .shopName(shop.getShopName())
                .shopLocation(shop.getShopLocation())
                .shopFlag(shop.getShopFlag())
                .shopOpenTime(shop.getShopOpenTime())
                .businessNum(shop.getBusinessNum())
                .createdAt(shop.getCreatedAt())
                .updatedAt(shop.getUpdatedAt())
                .employerCode(shop.getEmployerCode())
                .build();
    }
}
