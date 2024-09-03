package com.intbyte.wizbuddy.security;

import com.intbyte.wizbuddy.user.service.UserService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private Environment env;
    private JwtUtil jwtUtil;

    @Autowired
    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, Environment env, JwtUtil jwtUtil) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.env = env;
        this.jwtUtil = jwtUtil;
    }

    /* 설명. 인가(Authoriazation)용 메소드(인증 필터 추가) */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        /* 설명. csrf 비활성화 */
        http.csrf((csrf) -> csrf.disable());

        /* 설명. 로그인 시 추가할 authenticationManager 만들기 */
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authorizeHttpRequests((authz) ->
                        authz
                                .requestMatchers(new AntPathRequestMatcher("/users/employee", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/employer", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/employer/", "GET")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/users/employee/", "GET")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/users/employer/edit", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/users/employer/delete", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/users/employee/edit", "PATCH")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/users/employee/delete", "PATCH")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/task", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/task", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/task", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/task/*", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/register/", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/shops", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/edit", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/delete", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/checklist/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/checklist", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/checklist", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/checklist/*", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/checklist/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/checklist/*/task/*", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/checklist/*/task/*", "DELETE")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/checklist/*/task/*", "PUT")).permitAll()

                                .anyRequest().authenticated()
                )
                /* 설명. authenticationManager 등록(UserDetails를 상속받는 Service 계층 + BCrypt 암호화) */
                .authenticationManager(authenticationManager)

                /* 설명. session 방식을 사용하지 않음(JWT Token 방식 사용 시 설정할 내용) */
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager));
        http.addFilterBefore(new JwtFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* 설명. 인증(Authentication)용 메소드(인증 필터 반환) */
    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }

}