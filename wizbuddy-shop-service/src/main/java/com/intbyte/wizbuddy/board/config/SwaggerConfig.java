package com.intbyte.wizbuddy.board.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/* 설명. http://localhost:8080/swagger-ui/index.html */
@OpenAPIDefinition(
        info = @Info(title = "WizBuddy 게시판 API 명세서",
                description = "WizBuddy 기능별 API 명세서"))
@Configuration("BoardSwaggerConfiguration")
public class SwaggerConfig {

}