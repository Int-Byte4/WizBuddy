package com.intbyte.wizbuddy.taskperchecklist.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.intbyte.wizbuddy.taskperchecklist.query.repository", annotationClass = Mapper.class)
@Configuration("checkListMybatisConfiguration")
public class MybatisConfiguration {
}
