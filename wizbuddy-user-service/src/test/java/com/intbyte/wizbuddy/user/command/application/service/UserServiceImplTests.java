package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestRegisterEmployerVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.RequestSignInUserVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertEmployerVO;
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
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("사장유저 등록 성공")
    @Transactional
    void registerEmployerTestSuccess() {
        //given
        RequestSignInUserVO signInUserInfo = new RequestSignInUserVO("EMPLOYER", "test@test.com", "testPassword");

        RequestRegisterEmployerVO registerEmployerInfo = new RequestRegisterEmployerVO(
                "test"
                , signInUserInfo.getUserEmail()
                , signInUserInfo.getUserPassword()
                , "010-8888-8888"
                , true
                , false
                , LocalDateTime.now()
                , LocalDateTime.now()
        );

        //when
        ResponseInsertEmployerVO responseInsertEmployerVO = userServiceImpl.signInEmployer(signInUserInfo, registerEmployerInfo);

        //then
        UserEntity userEntity = userRepository.findById(responseInsertEmployerVO.getNewUser().getUserCode())
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        assertNotNull(userEntity);
    }

    @Test
    @DisplayName("직원유저 등록 성공")
    @Transactional
    void registerEmployeeTestSuccess() {
        //given
        RequestSignInUserVO signInUserInfo = new RequestSignInUserVO("EMPLOYEE", "test@test.com", "testPassword");

        RequestRegisterEmployeeVO registerEmployeeInfo = new RequestRegisterEmployeeVO(
                "test"
                , signInUserInfo.getUserEmail()
                , signInUserInfo.getUserPassword()
                , "010-8888-8888"
                , true
                , null
                , null
                , 0
                , null
                , false
                , LocalDateTime.now()
                , LocalDateTime.now());

        //when
        ResponseInsertEmployeeVO responseInsertEmployeeVO =  userServiceImpl.signInEmployee(signInUserInfo, registerEmployeeInfo);

        //then
        UserEntity userEntity = userRepository.findById(responseInsertEmployeeVO.getNewUser().getUserCode())
                .orElseThrow(() -> new CommonException(StatusEnum.USER_NOT_FOUND));

        assertNotNull(userEntity);
    }
}