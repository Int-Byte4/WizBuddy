package com.intbyte.wizbuddy.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /* 설명. 엔티티와 DTO 매핑을 위한 modelmapper 라이브러리 bean 객체 등록(dependency로 추가할 것) */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // 필드 접근을 활성화
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // private 필드에도 접근 가능
                .setMatchingStrategy(MatchingStrategies.STRICT); // 엄격한 매칭 전략
        return modelMapper;
    }
}
