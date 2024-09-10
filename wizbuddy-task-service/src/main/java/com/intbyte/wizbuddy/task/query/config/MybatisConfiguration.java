package com.intbyte.wizbuddy.task.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.intbyte.wizbuddy.task.query.repository", annotationClass = Mapper.class)
@Configuration("taskMybatisConfiguration")
public class MybatisConfiguration {

}
