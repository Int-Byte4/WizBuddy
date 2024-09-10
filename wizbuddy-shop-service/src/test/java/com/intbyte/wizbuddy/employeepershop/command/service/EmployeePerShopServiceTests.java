package com.intbyte.wizbuddy.employeepershop.command.service;

import com.intbyte.wizbuddy.employeepershop.command.application.dto.RequestEditEmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.command.application.service.EmployeePerShopService;
import com.intbyte.wizbuddy.employeepershop.command.domain.EmployeePerShopRepository;
import com.intbyte.wizbuddy.employeepershop.command.domain.entity.EmployeePerShop;
import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        EmployeePerShopDTO employeePerShopDTO = EmployeePerShopDTO.builder()
                .shopCode(2)
                .employeeCode(employeeCode)
                .shopHourlyWage(10000)
                .shopMonthlyWage(10)
                .build();

        //when
        employeePerShopService.insertEmployeePerShop(employeePerShopDTO);

        //then
        assertEquals(currentSize + 1, employeePerShopRepository.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("매장 별 직원 정보 수정 성공")
    public void deleteEmployeePerShopSuccessTest() {
        int shopCode = 1;
        String employeeCode = employeePerShopRepository.findAll().get(0).getEmployeeCode();

        RequestEditEmployeePerShopDTO info = RequestEditEmployeePerShopDTO.builder()
                .shopHourlyWage(11000)
                .shopMonthlyWage(11)
                .build();

        employeePerShopService.editEmployeePerShopById(employeeCode, info);

        List<EmployeePerShop> employeePerShop = employeePerShopRepository.findAll();

        System.out.println(employeePerShop.get(0));
    }
}