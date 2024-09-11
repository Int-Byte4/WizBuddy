package com.intbyte.wizbuddy.shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/* 설명. http://localhost:8080/swagger-ui/index.html */
@OpenAPIDefinition(
        info = @Info(title = "WizBuddy 매장 API 명세서",
                description = "WizBuddy 기능별 API 명세서"))
@Configuration("ShopSwaggerConfiguration")
public class SwaggerConfig {

}