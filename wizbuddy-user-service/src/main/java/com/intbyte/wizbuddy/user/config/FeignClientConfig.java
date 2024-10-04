package com.intbyte.wizbuddy.user.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    /* 설명. 다른 micro Service로 토큰을 이어서 가져가고 싶을 때 사용하자. */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                /* 설명. 현재 요청의 Http Servlet Request를 가져온다. */
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (requestAttributes != null) {
                    /* 설명. 현재 요청의 Authorization 헤더 추출 */
                    String authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

                    if (authorizationHeader != null) {

                        /* 설명. Feign client 요청에 "Authorization" 헤더 추가 */
                        requestTemplate.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
                    }
                }
            }
        };
    }
}
