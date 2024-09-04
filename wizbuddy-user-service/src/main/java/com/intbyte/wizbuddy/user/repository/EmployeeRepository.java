package com.intbyte.wizbuddy.user.repository;

import com.intbyte.wizbuddy.user.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
