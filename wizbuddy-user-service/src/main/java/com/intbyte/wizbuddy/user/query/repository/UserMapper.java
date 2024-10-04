package com.intbyte.wizbuddy.user.query.repository;

import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDTO getEmployer(String userCode, String userType);

    UserDTO getEmployee(String userCode, String userType);

    UserDTO getEmployeeByEmployeeCode(String employeeCode);
}
