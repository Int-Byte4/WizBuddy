package com.intbyte.wizbuddy.user.query.repository;

import com.intbyte.wizbuddy.user.command.domain.entity.Employer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployerMapper {
    Employer getEmployer(String employerCode);

//    Employer getEmployerByEmail(String employerEmail);

    List<Employer> getAllEmployer();
}
