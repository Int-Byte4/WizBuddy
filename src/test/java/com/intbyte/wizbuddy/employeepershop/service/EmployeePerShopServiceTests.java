package com.intbyte.wizbuddy.employeepershop.service;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.repository.EmployeePerShopRepository;
import com.intbyte.wizbuddy.mapper.EmployeePerShopMapper;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeePerShopServiceTests {
    @Autowired
    private EmployeePerShopService employeePerShopService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePerShopRepository employeePerShopRepository;
    @Qualifier("employerRepository")
    @Autowired
    private EmployerRepository employerRepository;

    @Test
    @Transactional
    @DisplayName("매장 별 직원에 직원 추가 테스트 성공")
    public void insertEmployeePerShopSuccessTest() {
        //given
        int currentSize = employeePerShopRepository.findAll().size();
        String employeeCode = employeeRepository.findAll().get(0).getEmployeeCode();
        String employerCode = employerRepository.findAll().get(1).getEmployerCode();

        EmployeePerShopDTO employeePerShopDTO = new EmployeePerShopDTO(2, employeeCode, 10000, 10);

        //when
        employeePerShopService.insertEmployeePerShop(employerCode, employeePerShopDTO);

        //then
        assertEquals(currentSize + 1, employeePerShopRepository.findAll().size());
    }

    @Test
    @DisplayName("매장 별 직원 전체 조회 성공")
    public void findAllEmployeePerShopSuccessTest() {
        List<EmployeePerShopDTO> employeePerShopList = employeePerShopService.findAllEmployeePerShop();
        for (EmployeePerShopDTO employeePerShopDTO : employeePerShopList) {
            System.out.println(employeePerShopDTO);
        }
    }

    @Test
    @DisplayName("매장 별 직원 직원코드로 조회 성공")
    public void findEmployeePerShopByCodeSuccessTest() {
        //given
        EmployeePerShopId employeePerShopId = new EmployeePerShopId(2, "20240831-187e-452d-88b4-62b7469c1cfa");

        //when
        EmployeePerShopDTO employeePerShopDTO = employeePerShopService.findEmployeePerShopById(employeePerShopId.getEmployeeCode());

        //then
        assertEquals(employeePerShopDTO.getEmployeeCode(), "20240831-187e-452d-88b4-62b7469c1cfa");
    }

    @Test
    @Transactional
    @DisplayName("매장 별 직원 정보 수정 성공")
    public void deleteEmployeePerShopSuccessTest() {
        int shopCode = 1;
        String employeeCode = employeeRepository.findAll().get(0).getEmployeeCode();

        EditEmployeePerShopInfo info = new EditEmployeePerShopInfo(11000, 11);

        employeePerShopService.editEmployeePerShopById(shopCode, employeeCode, info);

        List<EmployeePerShop> employeePerShop = employeePerShopRepository.findAll();

        System.out.println(employeePerShop.get(0));
    }
}