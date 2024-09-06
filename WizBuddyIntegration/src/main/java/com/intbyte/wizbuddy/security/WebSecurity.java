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

        http.csrf((csrf) -> csrf.disable());

        /* 로그인 시 추가할 authenticationManager */
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authorizeHttpRequests((authz) ->
                        authz
                                .requestMatchers(new AntPathRequestMatcher("/users/employee", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/employer", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/employers", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/users/employees", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/users/employer", "GET")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/users/employee", "GET")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/users/employer/edit", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/users/employer/delete", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/users/employee/edit", "PATCH")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/users/employee/delete", "PATCH")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/task", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/task", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/task", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/*/task/*", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/manualboard", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/manualboard/shop/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/manualboard/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/manualboard/register", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/manualboard/update/*", "PATCH")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/schedule/schedules", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/schedule/schedules/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/schedule/schedules/users/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/schedule/regist", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/schedule/regist/employee", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/schedule/modify/*", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/schedule/modify", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/schedule/delete/*", "DELETE")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/subsboards", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/subsboards/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/subsboards/shop/*","GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/subsboards", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/subsboards/update/*", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/subsboards/delete/*", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/comments", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comments/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comments/subs/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comments/employee/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comments", "POST")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/comments/update/*", "PATCH")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/comments/delete/*", "PATCH")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/comments/adopt/*", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/manualBoard", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/shop/register/", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/shop/shops", "GET")).hasRole("ADMIN")
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
                                .requestMatchers(new AntPathRequestMatcher("/taskperchecklist/checklist/*/task/*", "PUT")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/employeepershop/register", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/employeepershop/list", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/employeepershop/", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/employeepershop/employee", "GET")).hasRole("EMPLOYEE")
                                .requestMatchers(new AntPathRequestMatcher("/employeepershop/modify/shop/", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/employeepershop/employer/*", "DELETE")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/manualboards/like", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/noticeboards/like", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/noticeboard", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/noticeboard/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/noticeboard/shop/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/noticeboard/register", "POST")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/noticeboard/update/*", "PATCH")).hasRole("EMPLOYER")
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .anyRequest().authenticated()
                )
                /* UserDetails를 상속받는 Service 계층 + BCrypt 암호화 */
                .authenticationManager(authenticationManager)
          
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager));
        http.addFilterBefore(new JwtFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* Authentication 용 메소드(인증 필터 반환) */
    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }

}