package com.intbyte.wizbuddy.user.query.service;

import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.query.repository.EmployeeMapper;
import com.intbyte.wizbuddy.user.command.domain.aggregate.Employee;
import com.intbyte.wizbuddy.user.query.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service("employeeQueryService")
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final ModelMapper mapper;

    public EmployeeDTO getByEmployeeCode(String employeeCode) {
        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new CommonException(StatusEnum.USER_NOT_FOUND);

        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);

        return employeeDTO;
    }

    public List<EmployeeDTO> findAllEmployee() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee employee : employeeMapper.getAllEmployee()) {
            EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);

            employeeDTOList.add(employeeDTO);
        }

        return employeeDTOList;
    }
}
