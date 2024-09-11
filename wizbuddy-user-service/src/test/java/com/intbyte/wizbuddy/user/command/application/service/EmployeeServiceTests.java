package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployeeDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.Employee;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("직원 정보 수정 성공")
    @Transactional
    void updateEmployerSuccess() {
        //given
        List<Employee> employees = employeeRepository.findAll();
        String employeeCode = employees.get(0).getEmployeeCode();

        RequestEditEmployeeDTO requestEditEmployeeDTO = RequestEditEmployeeDTO.builder()
                .employeePassword("test")
                .employeePhone("010-9999-9998")
                .employeeHealthDate(LocalDate.now())
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        employeeService.modifyEmployee(employeeCode, requestEditEmployeeDTO, employeeCode);

        //then
        List<Employee> newEmployees = employeeRepository.findAll();
        assertEquals(newEmployees.get(0).getEmployeePhone(), requestEditEmployeeDTO.getEmployeePhone());
    }

    @Test
    @DisplayName("직원 삭제 성공")
    @Transactional
    void testDeleteEmployeeSuccess() {
        //given
        List<Employee> employees = employeeRepository.findAll();
        String employeeCode = employees.get(0).getEmployeeCode();

        //when
        employeeService.deleteEmployee(employeeCode, employeeCode);

        //then
        List<Employee> newEmployees = employeeRepository.findAll();
        assertEquals(false, newEmployees.get(0).isEmployeeFlag());
    }
}