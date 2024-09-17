package com.intbyte.wizbuddy.user.query.repository;

import com.intbyte.wizbuddy.user.command.domain.aggregate.Employer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployerMapper {
    Employer getEmployer(String employerCode);

    List<Employer> getAllEmployer();
}
