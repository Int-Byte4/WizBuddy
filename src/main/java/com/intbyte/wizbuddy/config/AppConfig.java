package com.intbyte.wizbuddy.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /* 설명. 엔티티와 DTO 매핑을 위한 modelmapper 라이브러리 bean 객체 등록(dependency로 추가할 것) */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}