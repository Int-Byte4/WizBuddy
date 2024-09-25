package com.intbyte.wizbuddy.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.intbyte.wizbuddy", annotationClass = Mapper.class)
public class MybatisConfiguration {

}
