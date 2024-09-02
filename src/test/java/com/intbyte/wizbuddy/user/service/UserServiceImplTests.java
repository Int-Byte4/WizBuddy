package com.intbyte.wizbuddy.user.service;

import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.domain.entity.Employee;
import com.intbyte.wizbuddy.user.domain.entity.Employer;
import com.intbyte.wizbuddy.user.domain.entity.User;
import com.intbyte.wizbuddy.user.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.dto.UserDTO;
import com.intbyte.wizbuddy.user.repository.EmployeeRepository;
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
class UserServiceImplTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("사장유저 등록 성공")
    @Transactional
    void registerEmployerTestSuccess() {
        //given
        UserDTO userDTO = new UserDTO("test", "EMPLOYER", "test@test.com", "testPassword");

        SignInUserInfo signInUserInfo = new SignInUserInfo(userDTO.getUsedCode(), userDTO.getUserType(), userDTO.getUserEmail(), userDTO.getUserPassword());

        EmployerDTO employerDTO = new EmployerDTO(userDTO.getUsedCode(), "test", userDTO.getUserEmail(), userDTO.getUserPassword(), "010-8888-8888:", true, false, LocalDateTime.now(), LocalDateTime.now());

        RegisterEmployerInfo registerEmployerInfo = new RegisterEmployerInfo(
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
        userServiceImpl.signInEmployer(signInUserInfo, registerEmployerInfo);

        //then
        List<User> newUsers = userRepository.findAll();
        User user = newUsers.get(newUsers.size() - 1);

        List<Employer> newEmployers = employerRepository.findAll();
        Employer employer = newEmployers.get(newEmployers.size() - 1);

        assertTrue(newUsers.contains(user));
        assertTrue(newEmployers.contains(employer));
    }

    @Test
    @DisplayName("직원유저 등록 성공")
    @Transactional
    void registerEmployeeTestSuccess() {
        //given
        UserDTO userDTO = new UserDTO("test", "EMPLOYEE", "test@test.com", "testPassword");

        SignInUserInfo signInUserInfo = new SignInUserInfo(userDTO.getUsedCode(), userDTO.getUserType(), userDTO.getUserEmail(), userDTO.getUserPassword());

        EmployeeDTO employeeDTO = new EmployeeDTO(userDTO.getUsedCode(), "test",  userDTO.getUserEmail(), userDTO.getUserPassword(), "010-8888-8888:", true, null, null, 0, null, false, LocalDateTime.now(), LocalDateTime.now());

        RegisterEmployeeInfo registerEmployeeInfo = new RegisterEmployeeInfo(
                employeeDTO.getEmployeeCode()
                , employeeDTO.getEmployeeName()
                , employeeDTO.getEmployeeEmail()
                , employeeDTO.getEmployeePassword()
                , employeeDTO.getEmployeePhone()
                , employeeDTO.isEmployeeFlag()
                , employeeDTO.getLatitude()
                , employeeDTO.getLongitude()
                , employeeDTO.getEmployeeWage()
                , employeeDTO.getEmployeeHealthDate()
                , employeeDTO.isEmployeeBlackState()
                , employeeDTO.getCreatedAt()
                , employeeDTO.getUpdatedAt());

        //when
        userServiceImpl.signInEmployee(signInUserInfo, registerEmployeeInfo);

        //then
        List<User> newUsers = userRepository.findAll();
        User user = newUsers.get(newUsers.size() - 1);

        List<Employee> newEmployees = employeeRepository.findAll();
        Employee employee = newEmployees.get(newEmployees.size() - 1);

        assertTrue(newUsers.contains(user));
        assertTrue(newEmployees.contains(employee));
    }
}