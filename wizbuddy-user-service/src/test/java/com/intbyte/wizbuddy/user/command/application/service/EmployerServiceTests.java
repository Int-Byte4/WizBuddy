package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditUserDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.repository.UserRepository;
import com.intbyte.wizbuddy.user.query.service.EmployerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class EmployerServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사장 정보 수정 성공")
    @Transactional
    void updateEmployerSuccess() {
        //given
        List<UserEntity> employers = userRepository.findAll();
        String employerCode = employers.get(0).getUserCode();

        RequestEditUserDTO requestEditUserDTO = RequestEditUserDTO.builder()
                .userPassword("test")
                .userPhone("010-9999-9998")
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        userService.modifyUser(employerCode, requestEditUserDTO, employerCode);

        //then
        List<UserEntity> newEmployers = userRepository.findAll();
        assertEquals(newEmployers.get(0).getUserPhone(), requestEditUserDTO.getUserPhone());
    }

    @Test
    @DisplayName("사장 삭제 성공")
    @Transactional
    void testDeleteEmployerSuccess() {
        //given
        List<UserEntity> employers = userRepository.findAll();
        String employerCode = employers.get(0).getUserCode();

        //when
        userService.deleteUser(employerCode, employerCode);

        //then
        List<UserEntity> newEmployees = userRepository.findAll();
        assertFalse(newEmployees.get(0).isUserFlag());
    }
}