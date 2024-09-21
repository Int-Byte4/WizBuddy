package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditUserDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.EmployeeAdditional;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterUserVO;
import com.intbyte.wizbuddy.user.command.domain.repository.EmployeeAdditionalRepository;
import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.repository.UserRepository;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertUserVO;
import com.intbyte.wizbuddy.user.query.dto.EmployeeAdditionalDTO;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service("userCommandService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeAdditionalRepository employeeAdditionalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private StringRedisTemplate stringRedisTemplate;  // StringRedisTemplate 사용

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

    @Transactional
    @Override
    public void modifyUser(String userCode, RequestEditUserDTO userDTO, String authUserCode) {
        UserEntity user = validUser(userCode, authUserCode);

        user.modify(userDTO);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(String userCode, String authUserCode) {
        UserEntity user = validUser(userCode, authUserCode);

        user.removeRequest(user);
        userRepository.save(user);
    }

    @Override
    public UserEntity validUser(String userCode, String authUserCode) {
        UserEntity user = userRepository.findById(userCode)
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        if (!userCode.equals(authUserCode)) throw new CommonException(StatusEnum.RESTRICTED);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity loginUserEntity = userRepository.findByUserEmail(userEmail);

        if (loginUserEntity == null) throw new UsernameNotFoundException(userEmail + " 이메일 아이디의 유저는 존재하지 않습니다.");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        switch (loginUserEntity.getUserType()) {
            case EMPLOYEE -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
            case EMPLOYER -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
            case ADMIN -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
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
}
