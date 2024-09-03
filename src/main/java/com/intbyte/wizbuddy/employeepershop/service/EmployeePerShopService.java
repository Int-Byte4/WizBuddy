package com.intbyte.wizbuddy.employeepershop.service;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.repository.EmployeePerShopRepository;
import com.intbyte.wizbuddy.exception.shop.ShopNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeePerShopMapper;
import com.intbyte.wizbuddy.mapper.ShopMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeePerShopService {

    private final EmployeePerShopRepository employeePerShopRepository;
    private final ShopRepository shopRepository;
    private final EmployeePerShopMapper employeePerShopMapper;
    private final ShopMapper shopMapper;

    @Autowired
    public EmployeePerShopService(EmployeePerShopRepository employeePerShopRepository, ShopRepository shopRepository, EmployeePerShopMapper employeePerShopMapper, ShopMapper shopMapper) {
        this.employeePerShopRepository = employeePerShopRepository;
        this.shopRepository = shopRepository;
        this.employeePerShopMapper = employeePerShopMapper;
        this.shopMapper = shopMapper;
    }

    // 매장 별 직원 데이터 등록
    @Transactional
    public void insertEmployeePerShop(EmployeePerShopDTO employeePerShopDTO) {
        int shopCode = shopMapper.findByShopCode(employeePerShopDTO.getShopCode());
        String employeeCode = employeePerShopMapper.findEmployeeCodeByEmployeeCode(employeePerShopDTO.getEmployeeCode());

        EmployeePerShop employeePerShop = EmployeePerShop.builder()
                .shopCode(shopCode)
                .employeeCode(employeeCode)
                .shopHourlyWage(employeePerShopDTO.getShopHourlyWage())
                .shopMonthlyWage(employeePerShopDTO.getShopMonthlyWage())
                .build();

        employeePerShopRepository.save(employeePerShop);
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

    @Transactional
    public void editEmployeePerShopById(int shopCode, String employeeCode, EditEmployeePerShopInfo info) {
        int shopValue = shopMapper.findByShopCode(shopCode);
        String employeeValue = employeePerShopMapper.findEmployeeCodeByEmployeeCode(employeeCode);

        EmployeePerShopId employeePerShopId = new EmployeePerShopId(shopValue, employeeValue);
        EmployeePerShop employeePerShop = employeePerShopRepository.findById(employeePerShopId).orElseThrow(IllegalArgumentException::new);

        employeePerShop.modify(shopValue, employeeValue, info);

        employeePerShopRepository.save(employeePerShop);
    }

    @Transactional
    public void deleteEmployeePerShopByEmployerCode(String employeeCode) {
        String employeeValue = employeePerShopMapper.findEmployeeCodeByEmployeeCode(employeeCode);
        Shop foundShop = shopMapper.findByEmployerCode(employeeValue);

        if (foundShop == null) throw new ShopNotFoundException();

        Map<String, Object> param = new HashMap<>();
        param.put("shopCode", foundShop.getShopCode());
        param.put("employeeCode", employeeValue);

        EmployeePerShopDTO employeePerShopDTO = employeePerShopMapper.findEmployeePerShopByShopCodeAndEmployeeCode(param);
        EmployeePerShopId id = new EmployeePerShopId(employeePerShopDTO.getShopCode(), employeePerShopDTO.getEmployeeCode());

        EmployeePerShop employeePerShop = new EmployeePerShop(id.getShopCode(), id.getEmployeeCode(), employeePerShopDTO.getShopHourlyWage(), employeePerShopDTO.getShopMonthlyWage());

        employeePerShopRepository.delete(employeePerShop);
    }
}
