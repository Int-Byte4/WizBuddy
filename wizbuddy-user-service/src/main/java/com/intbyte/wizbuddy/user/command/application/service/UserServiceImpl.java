package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditUserDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.EmployeeAdditional;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterKakaoUserVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterUserVO;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployeeAdditionalRepository;
import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.repository.UserRepository;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertUserVO;
import com.intbyte.wizbuddy.user.query.dto.EmployeeAdditionalDTO;
import com.intbyte.wizbuddy.user.query.dto.KakaoUserDTO;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import com.intbyte.wizbuddy.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service("userCommandService")
public class UserServiceImpl implements UserService {

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    private final UserRepository userRepository;
    private final EmployeeAdditionalRepository employeeAdditionalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final StringRedisTemplate stringRedisTemplate;  // StringRedisTemplate 사용

    @Transactional
    @Override
    public ResponseInsertUserVO signInUser(RequestRegisterUserVO requestRegisterUserVO) {

        // Redis에서 이메일 인증 여부 확인
        String emailVerificationStatus = stringRedisTemplate.opsForValue().get(requestRegisterUserVO.getUserEmail());

        if (!"True".equals(emailVerificationStatus)) {
            log.error("이메일 인증이 완료되지 않았습니다: {}", requestRegisterUserVO.getUserEmail());
            throw new CommonException(StatusEnum.EMAIL_VERIFICATION_REQUIRED); // 이메일 인증이 필요하다는 커스텀 예외 던지기
        }

        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String customUserCode = currentDate + uuid.substring(8);

        if (userRepository.findByUserEmail(requestRegisterUserVO.getUserEmail()) != null) throw new CommonException(StatusEnum.EMAIL_DUPLICATE);

        requestRegisterUserVO.setUserPassword(bCryptPasswordEncoder.encode(requestRegisterUserVO.getUserPassword()));

        UserDTO userDTO = userDTOBuild(requestRegisterUserVO);
        userDTO.setUserCode(customUserCode);

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);

        if (requestRegisterUserVO.getUserType().equals(UserTypeEnum.EMPLOYER)) {
            userRepository.save(userEntity);
        }
        else {
            userRepository.save(userEntity);

            EmployeeAdditionalDTO employeeAdditionalDTO = EmployeeAdditionalDTO.builder()
                    .userCode(userEntity.getUserCode())
                    .latitude(null)
                    .longitude(null)
                    .employeeHealthDate(null)
                    .employeeWage(0)
                    .build();
            EmployeeAdditional employeeAdditional = modelMapper.map(employeeAdditionalDTO, EmployeeAdditional.class);

            employeeAdditionalRepository.save(employeeAdditional);
        }
        return new ResponseInsertUserVO(userDTO);
    }

    @Override
    public void registerKakaoUser(RequestRegisterKakaoUserVO requestRegisterUserVO) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String customUserCode = currentDate + uuid.substring(8);

        KakaoUserDTO userDTO = kakaoUserDTOBuild(requestRegisterUserVO);
        userDTO.setUserCode(customUserCode);

        requestRegisterUserVO.setUserPassword(bCryptPasswordEncoder.encode(requestRegisterUserVO.getUserPassword()));

        /* 설명. 필드값이 정확히 일치할 때만 매칭하기 위해 STRICT 모드 상태로 modelMapper를 설정한다.(STANDARD -> STRICT) */
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);       // 안하면 userCode에 담겨 버린다.

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setKakaoId(userDTO.getKakaoId());  // kakaoId 설정

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void modifyUser(String userCode, RequestEditUserDTO userDTO) {
        UserEntity user = userRepository.findById(userCode).orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        user.modify(userDTO);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(String userCode) {
        UserEntity user = userRepository.findById(userCode).orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        user.removeRequest(user);
        userRepository.save(user);
    }

    @Override
    public KakaoUserDTO processKakaoUser(String code) {
        log.info("코드 확인: {}", code);

        String accessToken = getKakaoAccessToken(code);
        Map<String, Object> userInfo = getKakaoUserInfo(accessToken);

        String kakaoId = String.valueOf(userInfo.get("id"));
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("nickname");

        if (email == null || email.isEmpty()) {
            email = kakaoId + "@kakao.com";
        }

        UserEntity userEntity = userRepository.findByKakaoId(kakaoId);

        if (userEntity == null) {
            KakaoUserDTO newUser = new KakaoUserDTO();
            newUser.setUserEmail(email);
            newUser.setUserName(name != null ? name : "KakaoUser");
            newUser.setUserPassword(UUID.randomUUID().toString());
            newUser.setUserFlag(true);
            newUser.setUserBlackState(false);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setUpdatedAt(LocalDateTime.now());
            newUser.setKakaoId(kakaoId);

            RequestRegisterKakaoUserVO newKakaoUser = modelMapper.map(newUser, RequestRegisterKakaoUserVO.class);
            registerKakaoUser(newKakaoUser);

            userEntity = userRepository.findByKakaoId(kakaoId);
        }

        KakaoUserDTO userDTO = new ModelMapper().map(userEntity, KakaoUserDTO.class);
        userDTO.setKakaoAccessToken(accessToken);  // Kakao access token 설정

        // JWT 토큰 생성
        String jwtToken = jwtUtil.kakaoGenerateToken(userDTO);
        userDTO.setJwtToken(jwtToken);

        return userDTO;
    }

    private String getKakaoAccessToken(String code) {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);  // Kakao Developer에서 얻은 클라이언트 ID
        params.add("client_secret", clientSecret);  // Kakao Developer에서 얻은 클라이언트 시크릿
        params.add("redirect_uri", redirectUri);  // 등록한 리다이렉트 URI
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    kakaoAuthUrl,
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            return (String) responseBody.get("access_token");
        } catch (HttpClientErrorException e) {
            log.error("Kakao API error: {}", e.getResponseBodyAsString());
            throw e;
        }
    }

    private Map<String, Object> getKakaoUserInfo(String accessToken) {
        String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    kakaoUserInfoUrl,
                    HttpMethod.POST,
                    kakaoUserInfoRequest,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
            Map<String, Object> profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", responseBody.get("id"));
            userInfo.put("nickname", profile != null ? profile.get("nickname") : null); // 지금은 id만 돼 있는데 nickname도 허용

            return userInfo;
        } catch (HttpClientErrorException e) {
            log.error("Kakao API error: {}", e.getResponseBodyAsString());
            throw e;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity loginUserEntity = userRepository.findByUserEmail(userEmail);

        if (loginUserEntity == null) throw new UsernameNotFoundException(userEmail + " 이메일 아이디의 유저는 존재하지 않습니다.");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        switch (loginUserEntity.getUserType()) {
            case EMPLOYEE -> grantedAuthorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
            case EMPLOYER -> grantedAuthorities.add(new SimpleGrantedAuthority("EMPLOYER"));
            case ADMIN -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
                grantedAuthorities.add(new SimpleGrantedAuthority("EMPLOYER"));
                grantedAuthorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
            }
        }

        return new User(loginUserEntity.getUserEmail(), loginUserEntity.getUserPassword(), true, true, true, true, grantedAuthorities);
    }

    private UserDTO userDTOBuild(RequestRegisterUserVO requestSignInUserVO) {
        return UserDTO.builder()
                .userType(requestSignInUserVO.getUserType())
                .userName(requestSignInUserVO.getUserName())
                .userEmail(requestSignInUserVO.getUserEmail())
                .userPassword(requestSignInUserVO.getUserPassword())
                .userPhone(requestSignInUserVO.getUserPhone())
                .userFlag(requestSignInUserVO.isUserFlag())
                .userBlackState(requestSignInUserVO.isUserBlackState())
                .createdAt(requestSignInUserVO.getCreatedAt())
                .updatedAt(requestSignInUserVO.getUpdatedAt())
                .build();
    }

    private KakaoUserDTO kakaoUserDTOBuild(RequestRegisterKakaoUserVO requestSignInUserVO) {
        return KakaoUserDTO.builder()
                .userName(requestSignInUserVO.getUserName())
                .userEmail(requestSignInUserVO.getUserEmail())
                .userPassword(requestSignInUserVO.getUserPassword())
                .userFlag(requestSignInUserVO.isUserFlag())
                .userBlackState(requestSignInUserVO.isUserBlackState())
                .createdAt(requestSignInUserVO.getCreatedAt())
                .updatedAt(requestSignInUserVO.getUpdatedAt())
                .kakaoId(requestSignInUserVO.getKakaoId())
                .kakaoAccessToken(requestSignInUserVO.getKakaoAccessToken())
                .jwtToken(requestSignInUserVO.getJwtToken())
                .build();
    }
}
