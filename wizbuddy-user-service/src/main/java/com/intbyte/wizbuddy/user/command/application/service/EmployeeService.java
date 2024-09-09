package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployeeDTO;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.query.repository.EmployeeMapper;
import com.intbyte.wizbuddy.user.command.domain.entity.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service("employeeCommandService")
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public void modifyEmployee(String employeeCode, RequestEditEmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        employee.modify(employeeDTO);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(String employeeCode) {
        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        employee.removeRequest(employee);
        employeeRepository.save(employee);
    }
}
