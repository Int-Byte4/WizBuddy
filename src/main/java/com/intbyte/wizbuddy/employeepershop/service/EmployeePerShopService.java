package com.intbyte.wizbuddy.employeepershop.service;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.employeepershop.domain.EmployeePerShopMybatis;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.repository.EmployeePerShopRepository;
import com.intbyte.wizbuddy.exception.schedule.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.exception.shop.ShopNotFoundException;
import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeePerShopMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeePerShopService {

    private final EmployeePerShopRepository employeePerShopRepository;
    private final EmployeeRepository employeeRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final EmployeePerShopMapper employeePerShopMapper;

    @Autowired
    public EmployeePerShopService(EmployeePerShopRepository employeePerShopRepository, EmployeeRepository employeeRepository, ShopRepository shopRepository, ModelMapper modelMapper, EmployeePerShopMapper employeePerShopMapper) {
        this.employeePerShopRepository = employeePerShopRepository;
        this.employeeRepository = employeeRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.employeePerShopMapper = employeePerShopMapper;
    }

    // 매장 별 직원 데이터 등록
    @Transactional
    public void insertEmployeePerShop(EmployeePerShopDTO employeePerShopDTO) {
        EmployeePerShopId employeePerShopId = new EmployeePerShopId(employeePerShopDTO.getShopCode(), employeePerShopDTO.getEmployeeCode());

        Shop shop = shopRepository.findById(employeePerShopDTO.getShopCode()).orElseThrow(ShopNotFoundException::new);
        Employee employee = employeeRepository.findById(employeePerShopDTO.getEmployeeCode()).orElseThrow(UserNotFoundException::new);

        EmployeePerShop employeePerShop = EmployeePerShop.builder()
                .shop(shop)
                .employee(employee)
                .employeePerShopId(employeePerShopId)
                .shopHourlyWage(employeePerShopDTO.getShopHourlyWage())
                .shopMonthlyWage(employeePerShopDTO.getShopMonthlyWage())
                .build();

        employeePerShopRepository.save(employeePerShop);
    }

    @Transactional
    public EmployeePerShopDTO findEmployeePerShopById(EmployeePerShopId employeePerShopId) {
        EmployeePerShopMybatis employee = employeePerShopMapper.findEmployeePerShopById(employeePerShopId.getShopCode(), employeePerShopId.getEmployeeCode());

        return modelMapper.map(employee, EmployeePerShopDTO.class);
    }

    @Transactional
    public List<EmployeePerShopDTO> findAllEmployeePerShop() {
        List<EmployeePerShopMybatis> employees = employeePerShopMapper.findAllEmployeePerShop();

        return employees.stream()
                .map(employeeList -> modelMapper.map(employeeList, EmployeePerShopDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void editEmployeePerShopById(int shopCode, String employeeCode, EditEmployeePerShopInfo info) {
        EmployeePerShopId id = new EmployeePerShopId(shopCode, employeeCode);

        EmployeePerShop employeePerShop = employeePerShopRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Employee findEmployee = employeeRepository.findById(info.getEmployeeCode()).orElseThrow(EmployeeCodeNotFoundException::new);

        employeePerShop.modify(info, findEmployee);

        employeePerShopRepository.save(employeePerShop);
    }
}
