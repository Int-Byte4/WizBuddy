package com.intbyte.wizbuddy.shop.command.application.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestRegisterShopDTO;
import com.intbyte.wizbuddy.shop.command.infrastructure.dto.EmployerDTO;
import com.intbyte.wizbuddy.shop.command.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.response.ResponseRegisterShopVO;
import com.intbyte.wizbuddy.shop.query.repository.ShopMapper;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestDeleteShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestEditShopDTO;
import com.intbyte.wizbuddy.shop.command.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.command.domain.ShopRepository;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.response.ResponseEditShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("shopCommandService")
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final UserServiceClient userServiceClient;

    @Autowired
    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper, UserServiceClient userServiceClient) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    public ResponseRegisterShopVO registerShop(String employerCode, RequestRegisterShopDTO shopInfo) {
        if (shopMapper.findByBusinessNum(shopInfo.getBusinessNum()) != null) throw new CommonException(StatusEnum.BUSINESS_NUM_DUPLICATE);
        EmployerDTO employerDTO = userServiceClient.getEmployer(employerCode).getBody();

        Shop shop = Shop.builder()
                .shopName(shopInfo.getShopName())
                .shopLocation(shopInfo.getShopLocation())
                .shopFlag(true)
                .shopOpenTime(shopInfo.getShopOpenTime())
                .businessNum(shopInfo.getBusinessNum())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .employerCode(employerDTO.getEmployerCode())
                .build();

        shopRepository.save(shop);

        return new ResponseRegisterShopVO(shopInfo);
    }

    @Transactional
    public ResponseEditShopVO modifyShop(String employerCode, RequestEditShopDTO modifyShopInfo) {
        int shopCode = modifyShopInfo.getShopCode();

        String employer = shopMapper.getEmployerCode(employerCode);
        Shop shop = shopMapper.findShopByShopCode(shopCode);

        validateRequest(employer, shop);

        shop.modify(modifyShopInfo);
        shopRepository.save(shop);

        return new ResponseEditShopVO(modifyShopInfo);
    }

    @Transactional
    public void deleteShop(String employerCode, RequestDeleteShopDTO requestDeleteShopDTO) {
        int shopCode = requestDeleteShopDTO.getShopCode();

        String employer = shopMapper.getEmployerCode(employerCode);
        Shop shop = shopMapper.findShopByShopCode(shopCode);

        validateRequest(employer, shop);

        shop.removeRequest(requestDeleteShopDTO);
        shopRepository.save(shop);
    }

    private void validateRequest(String employerCode, Shop shop) {
        if (!employerCode.equals(shop.getEmployerCode())) {
            throw new CommonException(StatusEnum.RESTRICTED);
        }
    }
}
