package com.intbyte.wizbuddy.user.command.application.controller;

import com.intbyte.wizbuddy.user.command.application.service.EmployeeService;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.request.*;
import com.intbyte.wizbuddy.user.security.JwtUtil;
import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployeeDTO;
import com.intbyte.wizbuddy.user.command.application.dto.RequestEditEmployerDTO;
import com.intbyte.wizbuddy.user.command.application.service.EmployerService;
import com.intbyte.wizbuddy.user.command.application.service.UserService;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertEmployeeVO;
import com.intbyte.wizbuddy.user.command.domain.aggregate.vo.response.ResponseInsertEmployerVO;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("userCommandController")
@RequestMapping("users")
public class UserController {

    private JwtUtil jwtUtil;
    private Environment env;
    private ModelMapper modelMapper;
    private UserService userService;
    private EmployerService employerService;
    private EmployeeService employeeService;

    @Autowired
    public UserController(JwtUtil jwtUtil, Environment env, ModelMapper modelMapper, UserService userService, EmployerService employerService, EmployeeService employeeService) {
        this.jwtUtil = jwtUtil;
        this.env = env;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.employerService = employerService;
        this.employeeService = employeeService;
    }

    @Operation(summary = "사장 회원가입")
    @PostMapping("/employer")
    public ResponseEntity<ResponseInsertEmployerVO> registerEmployer(@RequestBody RequestInsertEmployerVO request) {
        RequestSignInUserVO requestSignInUserVO = modelMapper.map(request.getNewUser(), RequestSignInUserVO.class);
        RequestRegisterEmployerVO requestRegisterEmployerVO = modelMapper.map(request.getNewEmployer(), RequestRegisterEmployerVO.class);

        ResponseInsertEmployerVO responseUser = userService.signInEmployer(requestSignInUserVO, requestRegisterEmployerVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(summary = "직원 회원가입")
    @PostMapping("/employee")
    public ResponseEntity<ResponseInsertEmployeeVO> registerEmployee(@RequestBody RequestInsertEmployeeVO request) {
        RequestSignInUserVO requestSignInUserVO = modelMapper.map(request.getNewUser(), RequestSignInUserVO.class);
        RequestRegisterEmployeeVO requestRegisterEmployeeVO = modelMapper.map(request.getNewEmployee(), RequestRegisterEmployeeVO.class);

        ResponseInsertEmployeeVO responseUser =  userService.signInEmployee(requestSignInUserVO, requestRegisterEmployeeVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Operation(summary = "사장 정보 수정")
    @PatchMapping("/{employerCode}/edit")
    public ResponseEntity<Void> modifyEmployer(@PathVariable("employerCode") String employerCode, @RequestParam String employerPassword, @RequestParam String employerPhone, Authentication authentication) {
        String authEmployerCode = authentication.getName();

        RequestEditEmployerDTO employerDTO = RequestEditEmployerDTO.builder()
                .employerPassword(employerPassword)
                .employerPhone(employerPhone)
                .build();

        employerService.modifyEmployer(employerCode, employerDTO, authEmployerCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "직원 정보 수정")
    @PatchMapping("/{employeeCode}/edit")
    public ResponseEntity<Void> modifyEmployee(@PathVariable("employeeCode") String employeeCode, @RequestParam String employeePassword, @RequestParam String employeePhone, @RequestParam LocalDate employeeHealthDate, Authentication authentication) {
        String authEmployerCode = authentication.getName();

        RequestEditEmployeeDTO employeeDTO = RequestEditEmployeeDTO.builder()
                .employeePassword(employeePassword)
                .employeePhone(employeePhone)
                .employeeHealthDate(employeeHealthDate)
                .build();

        employeeService.modifyEmployee(employeeCode, employeeDTO, authEmployerCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "사장 탈퇴 요청")
    @PatchMapping("/{employerCode}/delete")
    public ResponseEntity<Void> deleteEmployer(@PathVariable("employerCode") String employerCode, Authentication authentication) {
        String authEmployerCode = authentication.getName();

        employerService.deleteEmployer(employerCode, authEmployerCode);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "직원 탈퇴 요청")
    @PatchMapping("/{employeeCode}/delete")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeCode") String employeeCode, Authentication authentication) {
        String authEmployeeCode = authentication.getName();

        employeeService.deleteEmployee(employeeCode, authEmployeeCode);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}