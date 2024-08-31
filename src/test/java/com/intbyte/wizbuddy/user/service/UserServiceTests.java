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
class UserServiceTests {

    @Autowired
    private EmployeeRepository employeeRepository;

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
        userService.signInEmployer(signInUserInfo, registerEmployerInfo);

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

    @Test
    @DisplayName("직원유저 등록 성공")
    @Transactional
    void registerEmployeeTestSuccess() {
        //given
        List<User> currentUsers = userRepository.findAll();
        UserDTO userDTO = new UserDTO(currentUsers.size(), "Employee");

        SignInUserInfo signInUserInfo = new SignInUserInfo(userDTO.getUsedCode(), userDTO.getUserType());

        List<Employee> currentEmployee = employeeRepository.findAll();
        EmployeeDTO employeeDTO = new EmployeeDTO(currentEmployee.size(), "test", "test@test.com", "testPassword", "010-8888-8888:", true, null, null, 0, null, false, LocalDateTime.now(), LocalDateTime.now());

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
        userService.signInEmployee(signInUserInfo, registerEmployeeInfo);

        //then
        List<User> newUsers = userRepository.findAll();
        User user = newUsers.get(newUsers.size() - 1);

        List<Employee> newEmployees = employeeRepository.findAll();
        Employee employee = newEmployees.get(newEmployees.size() - 1);

        assertTrue(newUsers.contains(user));
        assertTrue(newEmployees.contains(employee));

        newUsers.forEach(System.out::println);
        newEmployees.forEach(System.out::println);
    }
}