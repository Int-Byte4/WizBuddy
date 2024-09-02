package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.EmployeeMapper;
import com.intbyte.wizbuddy.user.domain.DeleteEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.EditEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
import com.intbyte.wizbuddy.user.vo.response.ResponseDeleteEmployeeVO;
import com.intbyte.wizbuddy.user.vo.response.ResponseEditEmployeeVO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ModelMapper mapper;

    @Transactional
    public ResponseEditEmployeeVO modifyEmployee(EditEmployeeInfo modifyEmployeeInfo) {
        String employeeCode = modifyEmployeeInfo.getEmployeeCode();

        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new UserNotFoundException();

        employee.modify(modifyEmployeeInfo);
        employeeRepository.save(employee);

        ResponseEditEmployeeVO responseEditEmployeeVO = mapper.map(modifyEmployeeInfo, ResponseEditEmployeeVO.class);

        return responseEditEmployeeVO;
    }

    @Transactional
    public ResponseDeleteEmployeeVO deleteEmployee(DeleteEmployeeInfo deleteEmployeeInfo) {
        String employeeCode = deleteEmployeeInfo.getEmployeeCode();

        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new UserNotFoundException();

        employee.removeRequest(deleteEmployeeInfo);
        employeeRepository.save(employee);

        ResponseDeleteEmployeeVO responseDeleteEmployeeVO = mapper.map(deleteEmployeeInfo, ResponseDeleteEmployeeVO.class);

        return responseDeleteEmployeeVO;
    }

    public EmployeeDTO getByEmployeeCode(String employeeCode) {
        Employee employee = employeeMapper.getEmployee(employeeCode);

        if (employee == null) throw new UserNotFoundException();

        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);

        return employeeDTO;
    }
}
