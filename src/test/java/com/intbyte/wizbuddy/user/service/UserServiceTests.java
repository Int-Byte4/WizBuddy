package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.SignInEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.domain.entity.User;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.dto.UserDTO;
import com.intbyte.wizbuddy.user.repository.EmployerRepository;
import com.intbyte.wizbuddy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTests {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사장유저 등록 성공")
    @Transactional
    void registerEmployerTestSuccess() {
        //given
        List<User> currentUsers = userRepository.findAll();
        UserDTO userDTO = new UserDTO(currentUsers.size(), "Employer");

        SignInUserInfo signInUserInfo = new SignInUserInfo(userDTO.getUsedCode(), userDTO.getUserType());

        List<Employer> currentEmployer = employerRepository.findAll();
        EmployerDTO employerDTO = new EmployerDTO(currentEmployer.size(), "test", "test@test.com", "testPassword", "010-8888-8888:", true, false, LocalDateTime.now(), LocalDateTime.now());

        SignInEmployerInfo signInEmployerInfo = new SignInEmployerInfo(
                employerDTO.getEmployerCode()
                , employerDTO.getEmployerName()
                , employerDTO.getEmployerEmail()
                , employerDTO.getEmployerPassword()
                , employerDTO.getEmployerPhone()
                , employerDTO.isEmployerFlag()
                , employerDTO.isEmployerBlackState()
                , employerDTO.getCreatedAt()
                , employerDTO.getUpdatedAt());

        //when
        userService.signInEmployer(signInUserInfo, signInEmployerInfo);

        //then
        List<User> newUsers = userRepository.findAll();
        User user = newUsers.get(newUsers.size() - 1);

        List<Employer> newEmployers = employerRepository.findAll();
        Employer employer = newEmployers.get(newEmployers.size() - 1);

        assertTrue(newUsers.contains(user));
        assertTrue(newEmployers.contains(employer));

        newUsers.forEach(System.out::println);
        newEmployers.forEach(System.out::println);
    }
}