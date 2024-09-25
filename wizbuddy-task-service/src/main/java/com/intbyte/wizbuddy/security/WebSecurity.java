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
                                .requestMatchers(new AntPathRequestMatcher("/checklist/**", "GET")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/checklist/**", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/checklist/**", "DELETE")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/checklist/**", "PUT")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/task/**", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/task/**", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/**", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/**", "PUT")).permitAll()
                                .anyRequest().authenticated()  // 모든 요청에 인증 요구
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // 세션을 사용하지 않음

        // 직접 생성한 JwtFilter를 필터 체인에 추가
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}