package com.intbyte.wizbuddy.user.repository;

import com.intbyte.wizbuddy.user.domain.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {
    Optional<Object> findByEmployerEmail(String employerEmail);
}
