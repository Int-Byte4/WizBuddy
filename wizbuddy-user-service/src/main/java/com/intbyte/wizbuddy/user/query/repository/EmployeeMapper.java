package com.intbyte.wizbuddy.user.query.repository;

import com.intbyte.wizbuddy.user.command.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    Employee getEmployee(String employeeCode);

//    Employee getEmployeeByEmail(String employeeEmail);

    List<Employee> getAllEmployee();
}
