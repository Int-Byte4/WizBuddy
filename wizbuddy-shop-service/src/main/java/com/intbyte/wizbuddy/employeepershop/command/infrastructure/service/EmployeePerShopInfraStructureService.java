package com.intbyte.wizbuddy.employeepershop.command.infrastructure.service;

import com.intbyte.wizbuddy.shop.query.dto.ShopDTO;

public interface EmployeePerShopInfraStructureService {

    ShopDTO getShop(int shopCode);
}