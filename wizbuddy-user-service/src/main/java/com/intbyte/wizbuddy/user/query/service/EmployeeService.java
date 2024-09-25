package com.intbyte.wizbuddy.user.query.service;

import com.intbyte.wizbuddy.user.command.domain.aggregate.EmployeeAdditional;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import com.intbyte.wizbuddy.user.query.repository.EmployeeMapper;
import com.intbyte.wizbuddy.user.query.dto.EmployeeAdditionalDTO;
import com.intbyte.wizbuddy.user.query.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service("employeeQueryService")
public class EmployeeService {

    private final UserMapper userMapper;
    private final EmployeeMapper employeeMapper;
    private final ModelMapper mapper;

    public List<Map<String, Object>> findAllEmployeeUser() {
        List<Map<String, Object>> employeeUserList = new ArrayList<>();

        for (EmployeeAdditionalDTO employeeAdditionalDTO : employeeMapper.getAllEmployeeDetail()) {
            Map<String, Object> combinedData = new HashMap<>();
            Map<String, Object> employeeUserMap = new HashMap<>();

            UserDTO userDTO = userMapper.getEmployee(employeeAdditionalDTO.getUserCode(), UserTypeEnum.EMPLOYEE.getUserType());

            combinedData.put("employeeAdditional", employeeAdditionalDTO);
            combinedData.put("user", userDTO);

            employeeUserMap.put("employeeData", combinedData);

            employeeUserList.add(employeeUserMap);
        }
        return employeeUserList;
    }


    public Map<String, Object> getByEmployeeCode(String employeeCode) {
        Map<String, Object> employeeUserMap = new HashMap<>();
        Map<String, Object> combinedData = new HashMap<>();

        EmployeeAdditionalDTO employeeAdditionalDTO = employeeMapper.getEmployeeDetail(employeeCode);
        UserDTO userDTO = userMapper.getEmployeeByEmployeeCode(employeeCode);

        combinedData.put("employeeAdditional", employeeAdditionalDTO);
        combinedData.put("user", userDTO);
        employeeUserMap.put(userDTO.getUserName(), combinedData);

        return employeeUserMap;
    }
}
