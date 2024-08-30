package com.intbyte.wizbuddy.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAndEmployerMapper {
    List<Integer> findByEmployerCode(int employerCode);
}
