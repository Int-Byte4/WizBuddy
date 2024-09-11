package com.intbyte.wizbuddy.board.query.config;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("SubsBoardMybatisConfiguration")
@MapperScan(basePackages = "com.intbyte.wizbuddy.board.query.domain.repository", annotationClass = Mapper.class)
public class SubsBoardMybatisConfiguration {

}
