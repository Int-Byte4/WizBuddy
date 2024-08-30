package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeeMapper;
import com.intbyte.wizbuddy.user.domain.DeleteEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public Employee registerEmployee(EmployeeDTO employeeInfo) {
        Employee employee = Employee.builder()
                .employeeName(employeeInfo.getEmployeeName())
                .employeeEmail(employeeInfo.getEmployeeEmail())
                .employeePassword(employeeInfo.getEmployeePassword())
                .employeePhone(employeeInfo.getEmployeePhone())
                .employeeFlag(employeeInfo.isEmployeeFlag())
                // 중간에 4개 컬럼은 nullable이라 생략
                .employeeBlackState(employeeInfo.isEmployeeBlackState())
                .createdAt(employeeInfo.getCreatedAt())
                .updatedAt(employeeInfo.getUpdatedAt())
                .build();

        return employeeRepository.save(employee);
    }

    @Transactional
    public void modifyEmployee(EditEmployeeInfo modifyEmployeeInfo) {
        int employeeCode = modifyEmployeeInfo.getEmployeeCode();

        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new UserNotFoundException();

        employee.modify(modifyEmployeeInfo);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(DeleteEmployeeInfo deleteEmployeeInfo) {
        int employeeCode = deleteEmployeeInfo.getEmployeeCode();

        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new UserNotFoundException();

        employee.removeRequest(deleteEmployeeInfo);
        employeeRepository.save(employee);
    }
}
