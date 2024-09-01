package com.intbyte.wizbuddy.schedule.repository;

import com.intbyte.wizbuddy.schedule.domain.entity.EmployeeWorkingPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeWorkingPartRepository extends JpaRepository<EmployeeWorkingPart, Integer> {
}
