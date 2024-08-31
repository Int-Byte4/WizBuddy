package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.UserAndEmployeeMapper;
import com.intbyte.wizbuddy.mapper.UserAndEmployerMapper;
import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAndEmployerMapper userAndEmployerMapper;
    private final UserAndEmployeeMapper userAndEmployeeMapper;

    public void signInEmployer(SignInUserInfo signInUserInfo, RegisterEmployerInfo registerEmployerInfo) {
        if (!userRepository.existsById(signInUserInfo.getUserCode())) throw new UserNotFoundException();

        userAndEmployerMapper.insertUser(signInUserInfo);

        registerEmployerInfo.setEmployerCode(signInUserInfo.getUserCode());
        userAndEmployerMapper.insertEmployer(registerEmployerInfo);
    }

    public void signInEmployee(SignInUserInfo signInUserInfo, RegisterEmployeeInfo registerEmployeeInfo) {
        if (!userRepository.existsById(signInUserInfo.getUserCode())) throw new UserNotFoundException();

        userAndEmployeeMapper.insertUser(signInUserInfo);

        registerEmployeeInfo.setEmployeeCode(signInUserInfo.getUserCode());
        userAndEmployeeMapper.insertEmployee(registerEmployeeInfo);
    }
}
