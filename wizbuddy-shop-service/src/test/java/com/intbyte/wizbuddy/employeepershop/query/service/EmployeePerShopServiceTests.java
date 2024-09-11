package com.intbyte.wizbuddy.employeepershop.query.service;

import com.intbyte.wizbuddy.employeepershop.command.domain.entity.EmployeePerShopId;
import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EmployeePerShopServiceTests {

    @Autowired
    private EmployeePerShopService employeePerShopService;

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
}