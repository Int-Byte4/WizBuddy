package com.intbyte.wizbuddy.employeeworkingpart.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("queryMybatisConfiguration")
@MapperScan(basePackages = "com.intbyte.wizbuddy.employeeworkingpart.query.repository", annotationClass = Mapper.class)
public class MybatisConfiguration {

}
