package com.intbyte.wizbuddy.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    /* 설명. VO <-> DTO <-> Entity, 필요한 순간에 의존성 주입을 받기 위해 bean으로 등록 */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    /* 설명. Security 자체에서 사용할 암호화 방식용 bean 추가 */
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}