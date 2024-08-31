package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.EmailDuplicateException;
import com.intbyte.wizbuddy.mapper.EmployeeMapper;
import com.intbyte.wizbuddy.mapper.EmployerMapper;
import com.intbyte.wizbuddy.mapper.UserAndEmployeeMapper;
import com.intbyte.wizbuddy.mapper.UserAndEmployerMapper;
import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserAndEmployerMapper userAndEmployerMapper;
    private final UserAndEmployeeMapper userAndEmployeeMapper;
    private final EmployerMapper employerMapper;
    private final EmployeeMapper employeeMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public void signInEmployer(SignInUserInfo signInUserInfo, RegisterEmployerInfo registerEmployerInfo) {
        signInUserInfo.setUserCode(UUID.randomUUID().toString());

        if (employerMapper.getEmployerByEmail(registerEmployerInfo.getEmployerEmail()) != null) throw new EmailDuplicateException();

        registerEmployerInfo.setEmployerCode(signInUserInfo.getUserCode());
        registerEmployerInfo.setEmployerPassword(bCryptPasswordEncoder.encode(registerEmployerInfo.getEmployerPassword()));

        userAndEmployerMapper.insertUser(signInUserInfo);
        userAndEmployerMapper.insertEmployer(registerEmployerInfo);
    }

    @Transactional
    public void signInEmployee(SignInUserInfo signInUserInfo, RegisterEmployeeInfo registerEmployeeInfo) {
        signInUserInfo.setUserCode(UUID.randomUUID().toString());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        if (employeeMapper.getEmployeeByEmail(registerEmployeeInfo.getEmployeeEmail()) != null) throw new EmailDuplicateException();

        registerEmployeeInfo.setEmployeeCode(signInUserInfo.getUserCode());
        registerEmployeeInfo.setEmployeePassword(bCryptPasswordEncoder.encode(registerEmployeeInfo.getEmployeePassword()));

        userAndEmployeeMapper.insertUser(signInUserInfo);
        userAndEmployeeMapper.insertEmployee(registerEmployeeInfo);
    }
}
