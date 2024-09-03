package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.EmailDuplicateException;
import com.intbyte.wizbuddy.mapper.EmployeeMapper;
import com.intbyte.wizbuddy.mapper.EmployerMapper;
import com.intbyte.wizbuddy.mapper.UserAndEmployeeMapper;
import com.intbyte.wizbuddy.mapper.UserAndEmployerMapper;
import com.intbyte.wizbuddy.user.domain.info.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.info.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.info.SignInUserInfo;
import com.intbyte.wizbuddy.user.repository.UserRepository;
import com.intbyte.wizbuddy.user.vo.response.ResponseInsertEmployeeVO;
import com.intbyte.wizbuddy.user.vo.response.ResponseInsertEmployerVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAndEmployerMapper userAndEmployerMapper;
    private final UserAndEmployeeMapper userAndEmployeeMapper;
    private final EmployerMapper employerMapper;
    private final EmployeeMapper employeeMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ResponseInsertEmployerVO signInEmployer(SignInUserInfo signInUserInfo, RegisterEmployerInfo registerEmployerInfo) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String customUserCode = currentDate + uuid.substring(8);

        signInUserInfo.setUserCode(customUserCode);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        if (employeeMapper.getEmployeeByEmail(registerEmployerInfo.getEmployerEmail()) != null && employeeMapper.getEmployeeByEmail(registerEmployerInfo.getEmployerEmail()) != null) throw new EmailDuplicateException();

        registerEmployerInfo.setEmployerCode(customUserCode);

        signInUserInfo.setUserPassword(bCryptPasswordEncoder.encode(signInUserInfo.getUserPassword()));
        registerEmployerInfo.setEmployerPassword(bCryptPasswordEncoder.encode(registerEmployerInfo.getEmployerPassword()));

        if (signInUserInfo.getUserType().equals("EMPLOYER")) signInUserInfo.setUserType("Employer");

        userAndEmployerMapper.insertUser(signInUserInfo);

        userAndEmployerMapper.insertEmployer(registerEmployerInfo);

        ResponseInsertEmployerVO registerEmployerVO = new ResponseInsertEmployerVO(signInUserInfo, registerEmployerInfo);

        return registerEmployerVO;
    }

    @Transactional
    @Override
    public ResponseInsertEmployeeVO signInEmployee(SignInUserInfo signInUserInfo, RegisterEmployeeInfo registerEmployeeInfo) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String uuid = UUID.randomUUID().toString();
        String customUserCode = currentDate + uuid.substring(8);  // uuid의 9번째 문자부터 끝까지 사용

        signInUserInfo.setUserCode(customUserCode);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        if (employeeMapper.getEmployeeByEmail(registerEmployeeInfo.getEmployeeEmail()) != null && employerMapper.getEmployerByEmail(registerEmployeeInfo.getEmployeeEmail()) != null) throw new EmailDuplicateException();

        registerEmployeeInfo.setEmployeeCode(customUserCode);

        signInUserInfo.setUserPassword(bCryptPasswordEncoder.encode(signInUserInfo.getUserPassword()));
        registerEmployeeInfo.setEmployeePassword(bCryptPasswordEncoder.encode(registerEmployeeInfo.getEmployeePassword()));

        userAndEmployeeMapper.insertUser(signInUserInfo);

        userAndEmployeeMapper.insertEmployee(registerEmployeeInfo);

        ResponseInsertEmployeeVO registerEmployeeVO = new ResponseInsertEmployeeVO(signInUserInfo, registerEmployeeInfo);

        return registerEmployeeVO;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        com.intbyte.wizbuddy.user.domain.entity.User loginUser = userRepository.findByUserEmail(userEmail);

        if (loginUser == null) throw new UsernameNotFoundException(userEmail + " 이메일 아이디의 유저는 존재하지 않습니다.");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        switch (loginUser.getUserType()) {
            case "Employee" -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
            case "Employer" -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
            case "Admin" -> {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYER"));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }

        return new User(loginUser.getUserEmail(), loginUser.getUserPassword(), true, true, true, true, grantedAuthorities);
    }
}
