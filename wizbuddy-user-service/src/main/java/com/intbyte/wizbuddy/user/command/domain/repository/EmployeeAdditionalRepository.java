package com.intbyte.wizbuddy.user.command.domain.repository;

import com.intbyte.wizbuddy.user.command.domain.aggregate.EmployeeAdditional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAdditionalRepository extends JpaRepository<EmployeeAdditional, String> {
}
