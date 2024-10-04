package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserTypeEnum;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterUserVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertUserVO;
import com.intbyte.wizbuddy.user.command.domain.repository.UserRepository;
import com.intbyte.wizbuddy.user.common.exception.CommonException;
import com.intbyte.wizbuddy.user.common.exception.StatusEnum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사장유저 등록 성공")
    @Transactional
    void registerEmployerTestSuccess() {
        //given
        RequestRegisterUserVO registerEmployerInfo = new RequestRegisterUserVO(
                UserTypeEnum.EMPLOYER,
                "test",
                "testEmail",
                "test",
                "010-9999-9999",
                true,
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        //when
        ResponseInsertUserVO responseInsertUserVO = userService.signInUser(registerEmployerInfo);

        //then
        UserEntity userEntity = userRepository.findById(responseInsertUserVO.getNewUser().getUserCode())
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        assertNotNull(userEntity);
    }

    @Test
    @DisplayName("직원유저 등록 성공")
    @Transactional
    void registerEmployeeTestSuccess() {
        //given
        RequestRegisterUserVO registerEmployeeInfo = new RequestRegisterUserVO(
                UserTypeEnum.EMPLOYEE,
                "test",
                "testEmail",
                "test",
                "010-9999-9999",
                true,
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        //when
        ResponseInsertUserVO responseInsertEmployeeVO =  userService.signInUser(registerEmployeeInfo);

        //then
        UserEntity userEntity = userRepository.findById(responseInsertEmployeeVO.getNewUser().getUserCode())
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        assertNotNull(userEntity);
    }
}