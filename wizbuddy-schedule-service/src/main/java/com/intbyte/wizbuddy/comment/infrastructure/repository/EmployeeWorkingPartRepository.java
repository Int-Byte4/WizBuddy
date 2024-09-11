package com.intbyte.wizbuddy.comment.infrastructure.repository;

import com.intbyte.wizbuddy.employeeworkingpart.command.domain.aggregate.entity.EmployeeWorkingPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("findCommentAdoptWorkingPart")
public interface EmployeeWorkingPartRepository extends JpaRepository<EmployeeWorkingPart, Integer> {

    List<EmployeeWorkingPart> findByEmployeeCode(String employeeCode);

    EmployeeWorkingPart findByWorkingPartCode(int employeeWorkingPartCode);
}
