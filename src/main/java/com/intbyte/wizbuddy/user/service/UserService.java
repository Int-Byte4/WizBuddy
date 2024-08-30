package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.exception.user.UserNotFoundException;
import com.intbyte.wizbuddy.mapper.UserAndEmployerMapper;
import com.intbyte.wizbuddy.user.domain.SignInEmployerInfo;
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

    public void signInEmployer(SignInUserInfo signInUserInfo, SignInEmployerInfo signInEmployerInfo) {
        if (!userRepository.existsById(signInUserInfo.getUserCode())) throw new UserNotFoundException();

        userAndEmployerMapper.insertUser(signInUserInfo);

        signInEmployerInfo.setEmployerCode(signInUserInfo.getUserCode());
        userAndEmployerMapper.insertEmployer(signInEmployerInfo);
    }
}
