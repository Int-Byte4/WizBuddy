package com.intbyte.wizbuddy.weeklyschedule.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.intbyte.wizbuddy.weeklyschedule.query.repository", annotationClass = Mapper.class)
public class MybatisConfiguration {

}
