package com.intbyte.wizbuddy.employeepershop.command.infrastructure.service;

import com.intbyte.wizbuddy.shop.query.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.query.service.ShopService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeePerShopInfraStructureService")
public class EmployeePerShopInfraStructureServiceImpl implements EmployeePerShopInfraStructureService {

    private final ShopService shopService;

    @Autowired
    public EmployeePerShopInfraStructureServiceImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    @Transactional
    public ShopDTO getShop(int shopCode) {
        return shopService.getShop(shopCode);
    }

}
