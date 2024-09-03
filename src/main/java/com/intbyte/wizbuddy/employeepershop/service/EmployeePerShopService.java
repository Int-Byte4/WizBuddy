package com.intbyte.wizbuddy.employeepershop.service;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.repository.EmployeePerShopRepository;
import com.intbyte.wizbuddy.exception.schedule.EmployeeCodeNotFoundException;
import com.intbyte.wizbuddy.exception.shop.ShopNotFoundException;
import com.intbyte.wizbuddy.exception.user.EmployerNotFoundException;
import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeePerShopMapper;
import com.intbyte.wizbuddy.mapper.ShopMapper;
import com.intbyte.wizbuddy.shop.domain.entity.Shop;
import com.intbyte.wizbuddy.shop.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.repository.ShopRepository;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
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
    private final EmployeeRepository employeeRepository;
    private final ShopRepository shopRepository;
    private final EmployeePerShopMapper employeePerShopMapper;
    private final EmployerRepository employerRepository;
    private final ShopMapper shopMapper;

    @Autowired
    public EmployeePerShopService(EmployeePerShopRepository employeePerShopRepository, EmployeeRepository employeeRepository, ShopRepository shopRepository, EmployeePerShopMapper employeePerShopMapper, @Qualifier("employerRepository") EmployerRepository employerRepository, ShopMapper shopMapper) {
        this.employeePerShopRepository = employeePerShopRepository;
        this.employeeRepository = employeeRepository;
        this.shopRepository = shopRepository;
        this.employeePerShopMapper = employeePerShopMapper;
        this.employerRepository = employerRepository;
        this.shopMapper = shopMapper;
    }

    // 매장 별 직원 데이터 등록
    @Transactional
    public void insertEmployeePerShop(String employerCode, EmployeePerShopDTO employeePerShopDTO) {
        EmployeePerShopId employeePerShopId = new EmployeePerShopId(employeePerShopDTO.getShopCode(), employeePerShopDTO.getEmployeeCode());

        employerRepository.findById(employerCode);
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
    public EmployeePerShopDTO findEmployeePerShopById(String employeeCode) {
        return employeePerShopMapper.findEmployeePerShopById(employeeCode);
    }

    @Transactional
    public List<EmployeePerShopDTO> findAllEmployeePerShop() {
        return employeePerShopMapper.findAllEmployeePerShop();
    }

    @Transactional
    public ShopDTO getShopByEmployeeCode(String employeeCode) {

        int shopCode = employeePerShopMapper.findShopCodeByEmployeeCode(employeeCode);

        Shop shop = shopRepository.findById(shopCode).orElseThrow(ShopNotFoundException::new);
        ShopDTO shopDTO = convertToShopDTO(shop);

        if (shopDTO == null) throw new ShopNotFoundException();


        return shopDTO;
    }

    @Transactional
    public void editEmployeePerShopById(int shopCode, String employeeCode, EditEmployeePerShopInfo info) {
        EmployeePerShopId id = new EmployeePerShopId(shopCode, employeeCode);

        EmployeePerShop employeePerShop = employeePerShopRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Employee findEmployee = employeeRepository.findById(id.getEmployeeCode()).orElseThrow(EmployeeCodeNotFoundException::new);

        employeePerShop.modify(info, findEmployee);

        employeePerShopRepository.save(employeePerShop);
    }

    @Transactional
    public void deleteEmployeePerShopByEmployerCode(String employerCode, String employeeCode) {
        Employer foundEmployer = employerRepository.findById(employerCode).orElseThrow(EmployerNotFoundException::new);
        Shop foundShop = shopMapper.findByEmployerCode(foundEmployer.getEmployerCode());

        if (foundShop == null) throw new ShopNotFoundException();

        Map<String, Object> param = new HashMap<>();
        param.put("shopCode", foundShop.getShopCode());
        param.put("employeeCode", employeeCode);

        EmployeePerShopDTO employeePerShopDTO = employeePerShopMapper.findByShopCode(param);
        EmployeePerShopId id = new EmployeePerShopId(employeePerShopDTO.getShopCode(), employeePerShopDTO.getEmployeeCode());
        Shop shop = shopRepository.findById(id.getShopCode()).orElseThrow(ShopNotFoundException::new);
        Employee employee = employeeRepository.findById(id.getEmployeeCode()).orElseThrow(EmployeeCodeNotFoundException::new);

        EmployeePerShop employeePerShop = new EmployeePerShop(id, shop, employee, employeePerShopDTO.getShopHourlyWage(), employeePerShopDTO.getShopMonthlyWage());

        employeePerShopRepository.delete(employeePerShop);
    }

    private ShopDTO convertToShopDTO(Shop shop) {
        return new ShopDTO(
                shop.getShopCode()
                , shop.getShopName()
                , shop.getShopLocation()
                , shop.getShopFlag()
                , shop.getShopOpenTime()
                , shop.getBusinessNum()
                , shop.getCreatedAt()
                , shop.getUpdatedAt()
                , shop.getEmployerCode());
    }
}
