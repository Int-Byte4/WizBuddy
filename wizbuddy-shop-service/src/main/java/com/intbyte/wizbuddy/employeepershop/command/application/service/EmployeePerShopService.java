package com.intbyte.wizbuddy.employeepershop.command.application.service;

import com.intbyte.wizbuddy.employeepershop.command.application.dto.RequestEditEmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.command.infrastructure.service.EmployeePerShopInfraStructureService;
import com.intbyte.wizbuddy.employeepershop.common.exception.CommonException;
import com.intbyte.wizbuddy.employeepershop.common.exception.StatusEnum;
import com.intbyte.wizbuddy.employeepershop.command.domain.EmployeePerShopRepository;
import com.intbyte.wizbuddy.employeepershop.command.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.command.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.query.repository.EmployeePerShopMapper;
import com.intbyte.wizbuddy.shop.domain.ShopRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("employeePerShopCommandService")
public class EmployeePerShopService {

    private final EmployeePerShopInfraStructureService employeePerShopInfraStructureService;
    private final EmployeePerShopRepository employeePerShopRepository;
    private final EmployeePerShopMapper employeePerShopMapper;
    private final ShopRepository shopRepository;

    @Autowired
    public EmployeePerShopService(EmployeePerShopInfraStructureService employeePerShopInfraStructureService, EmployeePerShopRepository employeePerShopRepository, EmployeePerShopMapper employeePerShopMapper, ShopRepository shopRepository) {
        this.employeePerShopInfraStructureService = employeePerShopInfraStructureService;
        this.employeePerShopRepository = employeePerShopRepository;
        this.employeePerShopMapper = employeePerShopMapper;
        this.shopRepository = shopRepository;
    }

    @Transactional
    public void insertEmployeePerShop(EmployeePerShopDTO employeePerShopDTO) {
        if (employeePerShopInfraStructureService.getShop(employeePerShopDTO.getShopCode()) == null) throw new CommonException(StatusEnum.SHOP_NOT_FOUND);
        if (employeePerShopMapper.findEmployeeCodeByEmployeeCode(employeePerShopDTO.getEmployeeCode()) == null) throw new CommonException(StatusEnum.USER_NOT_IN_SHOP);

        EmployeePerShop employeePerShop = EmployeePerShop.builder()
                .shopCode(employeePerShopDTO.getShopCode())
                .employeeCode(employeePerShopDTO.getEmployeeCode())
                .shopHourlyWage(employeePerShopDTO.getShopHourlyWage())
                .shopMonthlyWage(employeePerShopDTO.getShopMonthlyWage())
                .build();

        employeePerShopRepository.save(employeePerShop);
    }

    @Transactional
    public void editEmployeePerShopById(String employeeCode, RequestEditEmployeePerShopDTO info) {
        int shopValue = employeePerShopMapper.findShopCodeByEmployeeCode(employeeCode);
        String employeeValue = employeePerShopMapper.findEmployeeCodeByEmployeeCode(employeeCode);

        EmployeePerShopId employeePerShopId = new EmployeePerShopId(shopValue, employeeValue);
        EmployeePerShop employeePerShop = employeePerShopRepository.findById(employeePerShopId).orElseThrow(IllegalArgumentException::new);

        employeePerShop.modify(shopValue, employeeValue, info);

        employeePerShopRepository.save(employeePerShop);
    }

    @Transactional
    public void deleteEmployeePerShopByEmployeeCode(String employeeCode) {
        int shopCode = employeePerShopMapper.findShopCodeByEmployeeCode(employeeCode);

        if (shopRepository.findById(shopCode).isEmpty()) throw new CommonException(StatusEnum.SHOP_NOT_FOUND);

        Map<String, Object> param = new HashMap<>();
        param.put("shopCode", shopCode);
        param.put("employeeCode", employeeCode);

        EmployeePerShopDTO employeePerShopDTO = employeePerShopMapper.findEmployeePerShopByShopCodeAndEmployeeCode(param);
        EmployeePerShopId id = new EmployeePerShopId(employeePerShopDTO.getShopCode(), employeePerShopDTO.getEmployeeCode());

        EmployeePerShop employeePerShop = new EmployeePerShop(id.getShopCode(), id.getEmployeeCode(), employeePerShopDTO.getShopHourlyWage(), employeePerShopDTO.getShopMonthlyWage());

        employeePerShopRepository.delete(employeePerShop);
    }
}
