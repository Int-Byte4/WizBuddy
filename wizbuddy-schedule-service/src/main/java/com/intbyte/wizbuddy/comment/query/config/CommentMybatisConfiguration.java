package com.intbyte.wizbuddy.comment.query.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("CommentMybatisConfiguration")
@MapperScan(basePackages =  "com.intbyte.wizbuddy.comment.query.domain.repository", annotationClass = Mapper.class)
public class CommentMybatisConfiguration {


}
