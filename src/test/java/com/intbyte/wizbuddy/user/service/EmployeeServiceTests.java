package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.info.DeleteEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.info.EditEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        EditEmployeeInfo employeeInfo = new EditEmployeeInfo(employeeCode, "test", "test", "010-9999-9998", LocalDate.now(), LocalDateTime.now());

        //when
        employeeService.modifyEmployee(employeeInfo);

        //then
        List<Employee> newEmployees = employeeRepository.findAll();
        assertEquals(newEmployees.get(0).getEmployeeName(), employeeInfo.getEmployeeName());
    }

    @Test
    @DisplayName("직원 삭제 성공")
    @Transactional
    void testDeleteEmployeeSuccess() {
        //given
        List<Employee> employees = employeeRepository.findAll();
        String employeeCode = employees.get(0).getEmployeeCode();

        DeleteEmployeeInfo deleteEmployeeInfo = new DeleteEmployeeInfo(employeeCode,false, LocalDateTime.now());

        //when
        employeeService.deleteEmployee(deleteEmployeeInfo);

        //then
        List<Employee> newEmployees = employeeRepository.findAll();
        assertEquals(false, newEmployees.get(0).isEmployeeFlag());
    }
}