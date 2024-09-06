package com.intbyte.wizbuddy.user.repository;

import com.intbyte.wizbuddy.user.domain.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, String> {
}
