package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployeeDTO;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.command.domain.aggregate.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service("employeeCommandService")
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void modifyEmployee(String employeeCode, RequestEditEmployeeDTO employeeDTO, String authEmployeeCode) {
        Employee employee = getEmployee(employeeCode, authEmployeeCode);

        employee.modify(employeeDTO);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(String employeeCode, String authEmployeeCode) {
        Employee employee = getEmployee(employeeCode, authEmployeeCode);

        employee.removeRequest(employee);
        employeeRepository.save(employee);
    }

    private Employee getEmployee(String employeeCode, String authEmployeeCode) {
        Employee employee = employeeRepository.findById(employeeCode)
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        if (!employeeCode.equals(authEmployeeCode)) throw new CommonException(StatusEnum.RESTRICTED);
        return employee;
    }
}
