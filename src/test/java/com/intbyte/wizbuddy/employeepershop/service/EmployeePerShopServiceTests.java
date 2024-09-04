package com.intbyte.wizbuddy.employeepershop.service;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.repository.EmployeePerShopRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeePerShopServiceTests {
    @Autowired
    private EmployeePerShopService employeePerShopService;

    @Autowired
    private EmployeePerShopRepository employeePerShopRepository;

    @Test
    @Transactional
    @DisplayName("매장 별 직원에 직원 추가 테스트 성공")
    public void insertEmployeePerShopSuccessTest() {
        //given
        int currentSize = employeePerShopRepository.findAll().size();
        String employeeCode = employeePerShopRepository.findAll().get(0).getEmployeeCode();

        EmployeePerShopDTO employeePerShopDTO = new EmployeePerShopDTO(2, employeeCode, 10000, 10);

        //when
        employeePerShopService.insertEmployeePerShop(employeePerShopDTO);

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
    @DisplayName("매장 별 직원 직원코드로 직원이 속한 매장들 조회 성공")
    public void findEmployeePerShopByCodeSuccessTest() {
        //given
        EmployeePerShopId employeePerShopId = new EmployeePerShopId(2, "20240831-187e-452d-88b4-62b7469c1cfa");

        //when
        List<EmployeePerShopDTO> employeePerShopList = employeePerShopService.findEmployeePerShopById(employeePerShopId.getEmployeeCode());

        //then
        for (EmployeePerShopDTO employeePerShopDTO : employeePerShopList) {
            System.out.println(employeePerShopDTO);
        }
    }

    @Test
    @Transactional
    @DisplayName("매장 별 직원 정보 수정 성공")
    public void deleteEmployeePerShopSuccessTest() {
        int shopCode = 1;
        String employeeCode = employeePerShopRepository.findAll().get(0).getEmployeeCode();

        EditEmployeePerShopInfo info = new EditEmployeePerShopInfo(11000, 11);

        employeePerShopService.editEmployeePerShopById(shopCode, employeeCode, info);

        List<EmployeePerShop> employeePerShop = employeePerShopRepository.findAll();

        System.out.println(employeePerShop.get(0));
    }
}