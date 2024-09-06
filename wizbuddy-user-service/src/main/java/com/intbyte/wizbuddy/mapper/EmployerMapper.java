package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.entity.Employer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployerMapper {
    Employer getEmployer(String employerCode);

    Employer getEmployerByEmail(String employerEmail);
}
