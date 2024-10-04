package com.intbyte.wizbuddy.employeepershop.query.service;

import com.intbyte.wizbuddy.employeepershop.command.domain.EmployeePerShopRepository;
import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.query.repository.EmployeePerShopMapper;
import com.intbyte.wizbuddy.shop.query.repository.ShopMapper;
import com.intbyte.wizbuddy.shop.command.domain.ShopRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("employeePerShopQueryService")
public class EmployeePerShopService {

    private final EmployeePerShopRepository employeePerShopRepository;
    private final EmployeePerShopMapper employeePerShopMapper;
    private final ShopMapper shopMapper;
    private final ShopRepository shopRepository;

    @Autowired
    public EmployeePerShopService(EmployeePerShopRepository employeePerShopRepository, EmployeePerShopMapper employeePerShopMapper, ShopMapper shopMapper, @Qualifier("shopRepository") ShopRepository shopRepository) {
        this.employeePerShopRepository = employeePerShopRepository;
        this.employeePerShopMapper = employeePerShopMapper;
        this.shopMapper = shopMapper;
        this.shopRepository = shopRepository;
    }

    @Transactional
    public List<EmployeePerShopDTO> findEmployeePerShopById(String employeeCode) {
        return employeePerShopMapper.findEmployeePerShopById(employeeCode);
    }

    @Transactional
    public List<EmployeePerShopDTO> findAllEmployeePerShop() {
        return employeePerShopMapper.findAllEmployeePerShop();
    }

    @Transactional
    public EmployeePerShopDTO getEmployeePerShopByEmployeeCode(int shopCode, String employeeCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("employeeCode", employeeCode);
        params.put("shopCode", shopCode);

        return employeePerShopMapper.findEmployeePerShopByShopCodeAndEmployeeCode(params);

    }
}
