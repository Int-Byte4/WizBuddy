package com.intbyte.wizbuddy.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intbyte.wizbuddy.user.command.application.service.UserService;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestLoginVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    /* 설명. 로그인 시도 시 동작하는 기능(POST /login 요청 시) */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        /* 설명. request body에 담긴 내용을 우리가 만든 RequestLoginVO 타입에 담는다.(일종의 @RequestBody의 개념) */
        try {
            RequestLoginVO creds = new ObjectMapper().readValue(request.getInputStream(), RequestLoginVO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getUserEmail(), creds.getUserPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* 설명. 로그인 성공 시 JWT 토큰 생성하는 기능 */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("로그인 성공하고 security가 관리하는 principal 객체(authResult): {}", authResult);

        /* 설명. 로그인 이후 관리되고 있는 Authentication 객체를 활용해 JWT Token 만들기 */
        log.info("시크릿 키: " + env.getProperty("token.secret"));

        /* 설명. 토큰의 payload에 어떤 값을 담고 싶은지에 따라 고민해서 재료를 수집한다.(id, 가진 권한들, 만료시간) */
        String userName = ((User)authResult.getPrincipal()).getUsername();  // id의 개념(우리는 email로 작성했음)
        log.info("인증된 회원의 id: " + userName);

        /* 설명. 권한들을 꺼내 List<String>로 변환 */
        List<String> roles = authResult.getAuthorities().stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());

        /* 설명. 재료들로 토큰 만들기(Jwt Token 라이브러리 추가(3가지)하기) */
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("auth", roles);      // 비공개 클레임은 Claims에서 제공하는 put을 활용해 추가한다.

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

//        response.addHeader("token", token);
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
    }
}
