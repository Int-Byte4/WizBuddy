package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.user.domain.entity.Employer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployerMapper {

    List<Integer> findEmployerByEmployerCode(int employerCode);

    Employer getEmployer(int employerCode);
}
