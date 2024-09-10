package com.intbyte.wizbuddy.checklist.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.intbyte.wizbuddy.checklist.query.repository", annotationClass = Mapper.class)
@Configuration("checkListMybatisConfiguration")
public class MybatisConfiguration {
}
