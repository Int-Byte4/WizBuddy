package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.EditEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployerInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
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
        int employeeCode = employees.get(0).getEmployeeCode();

        EditEmployeeInfo employeeInfo = new EditEmployeeInfo(employeeCode, "test", "test", "010-9999-9998", LocalDate.now(), LocalDateTime.now());

        //when
        employeeService.modifyEmployee(employeeInfo);

        //then
        List<Employee> newEmployees = employeeRepository.findAll();
        assertEquals(newEmployees.get(0).getEmployeeName(), employeeInfo.getEmployeeName());

        newEmployees.forEach(System.out::println);
    }
}