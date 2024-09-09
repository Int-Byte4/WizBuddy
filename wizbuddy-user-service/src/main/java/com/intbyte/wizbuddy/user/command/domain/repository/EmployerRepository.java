package com.intbyte.wizbuddy.user.command.domain.repository;

import com.intbyte.wizbuddy.user.command.domain.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, String> {
}
