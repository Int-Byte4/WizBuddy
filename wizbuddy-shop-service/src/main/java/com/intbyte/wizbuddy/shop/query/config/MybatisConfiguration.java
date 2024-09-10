package com.intbyte.wizbuddy.shop.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("shopMyBatisConfiguration")
@MapperScan(value = "com.intbyte.wizbuddy.shop.query.repository", annotationClass = Mapper.class)
public class MybatisConfiguration {

}
