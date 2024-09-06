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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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

    // 매장 별 직원 데이터 등록
    @Transactional
    public void insertEmployeePerShop(EmployeePerShopDTO employeePerShopDTO) {
        int shopCode = shopMapper.findByShopCode(employeePerShopDTO.getShopCode());
        String employeeCode = employeePerShopDTO.getEmployeeCode();

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
    public void deleteEmployeePerShopByEmployeeCode(String employeeCode) {
        int shopCode = employeePerShopMapper.findShopCodeByEmployeeCode(employeeCode);

        shopRepository.findById(shopCode).orElseThrow(ShopNotFoundException::new);

        Map<String, Object> param = new HashMap<>();
        param.put("shopCode", shopCode);
        param.put("employeeCode", employeeCode);

        EmployeePerShopDTO employeePerShopDTO = employeePerShopMapper.findEmployeePerShopByShopCodeAndEmployeeCode(param);
        EmployeePerShopId id = new EmployeePerShopId(employeePerShopDTO.getShopCode(), employeePerShopDTO.getEmployeeCode());

        EmployeePerShop employeePerShop = new EmployeePerShop(id.getShopCode(), id.getEmployeeCode(), employeePerShopDTO.getShopHourlyWage(), employeePerShopDTO.getShopMonthlyWage());

        employeePerShopRepository.delete(employeePerShop);
    }
}
