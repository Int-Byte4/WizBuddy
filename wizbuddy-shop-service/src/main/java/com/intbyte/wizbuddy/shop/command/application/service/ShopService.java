package com.intbyte.wizbuddy.shop.command.application.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.infrastructure.dto.UserDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestRegisterShopDTO;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.response.ResponseRegisterShopVO;
import com.intbyte.wizbuddy.shop.query.repository.ShopMapper;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestDeleteShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestEditShopDTO;
import com.intbyte.wizbuddy.shop.command.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.command.domain.ShopRepository;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.response.ResponseEditShopVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("shopCommandService")
@Slf4j
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
        UserDTO employerDTO = userServiceClient.getEmployer(employerCode).getBody();
        log.info(employerDTO.toString());
        String userCode = employerDTO.getUserCode();

        Shop shop = Shop.builder()
                .shopName(shopInfo.getShopName())
                .shopLocation(shopInfo.getShopLocation())
                .shopFlag(true)
                .shopOpenTime(shopInfo.getShopOpenTime())
                .businessNum(shopInfo.getBusinessNum())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .employerCode(userCode)
                .build();

        shopRepository.save(shop);

        return new ResponseRegisterShopVO(shopInfo);
    }

    @Transactional
    public ResponseEditShopVO modifyShop(String employerCode, RequestEditShopDTO modifyShopInfo) {
        int shopCode = modifyShopInfo.getShopCode();

        List<String> employerList = shopMapper.getEmployerCode(employerCode);
        Shop shop = shopMapper.findShopByShopCode(shopCode);

        validateRequest(employerList, shop);

        shop.modify(modifyShopInfo);
        shop.setUpdatedAt(LocalDateTime.now());

        shopRepository.save(shop);

        return new ResponseEditShopVO(modifyShopInfo);
    }

    @Transactional
    public void deleteShop(String employerCode, RequestDeleteShopDTO requestDeleteShopDTO) {
        int shopCode = requestDeleteShopDTO.getShopCode();

        List<String> employerList = shopMapper.getEmployerCode(employerCode);
        Shop shop = shopMapper.findShopByShopCode(shopCode);

        validateRequest(employerList, shop);

        shop.removeRequest(requestDeleteShopDTO);
        shop.setUpdatedAt(LocalDateTime.now());
        shop.setShopFlag(false);

        shopRepository.save(shop);
    }

    private void validateRequest(List<String> employerCodeList, Shop shop) {
        for (int i = 0; i < employerCodeList.size(); i++) {
            if (!employerCodeList.get(i).equals(shop.getEmployerCode()) && i == employerCodeList.size() - 1) {
                throw new CommonException(StatusEnum.RESTRICTED);
            }
        }
    }
}
