package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    Employee getEmployee(String employeeCode);

    Employee getEmployeeByEmail(String employeeEmail);

}
