package com.intbyte.wizbuddy.board.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("BoardMyBatisConfiguration")
@MapperScan(value = "com.intbyte.wizbuddy", annotationClass = Mapper.class)
public class MybatisConfiguration {

}
