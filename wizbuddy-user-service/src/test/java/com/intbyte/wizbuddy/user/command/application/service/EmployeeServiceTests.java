package com.intbyte.wizbuddy.user.command.application.service;

import com.intbyte.wizbuddy.user.command.application.dto.RequestEditUserDTO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.UserEntity;
import com.intbyte.wizbuddy.user.command.domain.repository.UserRepository;
import com.intbyte.wizbuddy.user.query.dto.UserDTO;
import com.intbyte.wizbuddy.user.query.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("직원 정보 수정 성공")
    @Transactional
    void updateEmployerSuccess() {
        //given
        List<Map<String, Object>> employeeList = employeeService.findAllEmployeeUser();

        Map<String, Object> employee = employeeList.get(0);
        Map<String, Object> employeeData = (Map<String, Object>) employee.get("employeeData");
        UserDTO employeeUser = (UserDTO) employeeData.get("user");

        String employeeUserCode = employeeUser.getUserCode();

        RequestEditUserDTO requestEditUserDTO = RequestEditUserDTO.builder()
                .userPassword("test")
                .userPhone("010-9999-9998")
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        userService.modifyUser(employeeUserCode, requestEditUserDTO);

        //then
        List<UserEntity> newEmployees = userRepository.findAll();
        assertEquals(newEmployees.get(0).getUserPhone(), requestEditUserDTO.getUserPhone());
    }

    @Test
    @DisplayName("직원 삭제 성공")
    @Transactional
    void testDeleteEmployeeSuccess() {
        //given
        List<Map<String, Object>> employeeList = employeeService.findAllEmployeeUser();

        Map<String, Object> employee = employeeList.get(0);
        Map<String, Object> employeeData = (Map<String, Object>) employee.get("employeeData");
        UserDTO employeeUser = (UserDTO) employeeData.get("user");

        String employeeUserCode = employeeUser.getUserCode();

        //when
        userService.deleteUser(employeeUserCode);

        //then
        List<UserEntity> newEmployees = userRepository.findAll();
        assertFalse(newEmployees.get(0).isUserFlag());
    }
}