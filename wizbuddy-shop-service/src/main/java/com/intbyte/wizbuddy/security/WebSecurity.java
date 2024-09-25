package com.intbyte.wizbuddy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurity {

    private final JwtUtil jwtUtil;

    public WebSecurity(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // JWT 필터를 직접 생성
        JwtFilter jwtFilter = new JwtFilter(jwtUtil);

        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authz) ->
                        authz
                                .requestMatchers(new AntPathRequestMatcher("/shop/register/", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/shops", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/edit", "PATCH")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/delete", "PATCH")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/checklist/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/checklist", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/checklist", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/checklist/*", "POST")).permitAll()
                        .anyRequest().authenticated()  // 모든 요청에 인증 요구
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // 세션을 사용하지 않음

        // 직접 생성한 JwtFilter를 필터 체인에 추가
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
