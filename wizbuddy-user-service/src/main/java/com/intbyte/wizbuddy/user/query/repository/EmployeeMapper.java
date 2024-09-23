package com.intbyte.wizbuddy.user.query.repository;

import com.intbyte.wizbuddy.user.command.domain.aggregate.EmployeeAdditional;
import com.intbyte.wizbuddy.user.query.dto.EmployeeAdditionalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeAdditional> getAllEmployeeDetail();

    EmployeeAdditionalDTO getEmployeeDetail(String employeeCode);
}
