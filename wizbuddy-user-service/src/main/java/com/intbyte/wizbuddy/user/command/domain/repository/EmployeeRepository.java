package com.intbyte.wizbuddy.user.command.domain.repository;

import com.intbyte.wizbuddy.user.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
