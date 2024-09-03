package com.intbyte.wizbuddy.schedule.repository;

import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeWorkingPartRepository extends JpaRepository<EmployeeWorkingPart, Integer> {

    EmployeeWorkingPart findByWorkingPartCode(int workingPartCode);

    List<EmployeeWorkingPart> findByEmployeeCode(String employeeCode);
}
