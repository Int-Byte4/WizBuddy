package com.intbyte.wizbuddy.employeeworkingpart.command.domain.repository;

import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeWorkingPartRepository extends JpaRepository<EmployeeWorkingPart, Integer> {

    List<EmployeeWorkingPart> findByEmployeeCode(String employeeCode);
}
