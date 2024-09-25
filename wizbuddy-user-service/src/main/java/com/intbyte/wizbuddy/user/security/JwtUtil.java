package com.intbyte.wizbuddy.user.security;

import com.intbyte.wizbuddy.user.command.application.service.UserService;
import com.intbyte.wizbuddy.user.query.dto.KakaoUserDTO;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

    private final Key secretKey;
    private long expirationTime;

    public JwtUtil(@Value("${token.secret}") String secretKey, @Value("${token.expiration_time}") long expirationTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationTime = expirationTime;
    }

    /* 설명. Token 검증(Bearer 토큰이 넘어왔고, 우리 사이트의 secret key로 만들어 졌는가, 만료되었는지와 내용이 비어있진 않은지) */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Token claims empty {}", e);
        }

        return false;
    }

    /* 설명. 넘어온 AccessToken으로 인증 객체 추출 */
    public Authentication getAuthentication(String token) {

        /* 설명. 토큰에서 claim들 추출 */
        Claims claims = parseClaims(token);
        log.info("넘어온 AccessToken claims 확인: {}", claims);

        // JWT 토큰에서 subject를 가져와 사용자 ID로 사용
        String userId = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities = null;
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        } else {
            /* 설명. 클레임에서 권한 정보들 가져오기 */
            authorities = Arrays.stream(claims.get("auth").toString()
                            .replace("[", "")
                            .replace("]", "")
                            .split(", "))
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
        }

        // SecurityContext에 인증 정보를 저장하기 위해 Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(userId, "", authorities);
    }


    /* 설명. Token에서 Claims 추출 */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    /* 설명. Token에서 사용자의 id(subject 클레임) 추출 */
    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    public String kakaoGenerateToken(KakaoUserDTO userDTO) {
        Claims claims = Jwts.claims().setSubject(userDTO.getUserCode());
        claims.put("email", userDTO.getUserEmail());
        claims.put("name", userDTO.getUserName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}