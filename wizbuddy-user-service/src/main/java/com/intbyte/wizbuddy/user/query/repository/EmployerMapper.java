package com.intbyte.wizbuddy.user.query.repository;

import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployerMapper {
    List<UserEntity> getAllEmployer();
}
