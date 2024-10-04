package com.intbyte.wizbuddy.user.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("userServiceMybatisConfiguration")
@MapperScan(value = "com.intbyte.wizbuddy", annotationClass = Mapper.class)
public class MybatisConfiguration {

}
