package com.intbyte.wizbuddy.user.controller;

import com.intbyte.wizbuddy.user.domain.RegisterEmployeeInfo;
import com.intbyte.wizbuddy.user.domain.RegisterEmployerInfo;
import com.intbyte.wizbuddy.user.domain.SignInUserInfo;
import com.intbyte.wizbuddy.user.dto.UserDTO;
import com.intbyte.wizbuddy.user.service.EmployeeService;
import com.intbyte.wizbuddy.user.service.EmployerService;
import com.intbyte.wizbuddy.user.service.UserService;
import com.intbyte.wizbuddy.vo.request.RegisterEmployeeRequest;
import com.intbyte.wizbuddy.vo.request.RegisterEmployerRequest;
import com.intbyte.wizbuddy.vo.response.ResponseFindEmployeeVO;
import com.intbyte.wizbuddy.vo.response.ResponseFindEmployerVO;
import com.intbyte.wizbuddy.vo.response.ResponseRegisterEmployeeVO;
import com.intbyte.wizbuddy.vo.response.ResponseRegisterEmployerVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private Environment env;
    private ModelMapper modelMapper;
    private UserService userService;
    private EmployerService employerService;
    private EmployeeService employeeService;

    @Autowired
    public UserController(Environment env, ModelMapper modelMapper, UserService userService, EmployerService employerService, EmployeeService employeeService) {
        this.env = env;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.employerService = employerService;
        this.employeeService = employeeService;
    }

    /* 설명. 로그인 기능(feat. security 모듈 활용) 전에 회원가입 기능 만들기 */
    @PostMapping("users/employer")
    public ResponseEntity<ResponseRegisterEmployerVO> registerEmployer(@RequestBody RegisterEmployerRequest request) {
        SignInUserInfo signInUserInfo = modelMapper.map(request.getNewUser(), SignInUserInfo.class);
        RegisterEmployerInfo registerEmployerInfo = modelMapper.map(request.getNewEmployer(), RegisterEmployerInfo.class);

        userService.signInEmployer(signInUserInfo, registerEmployerInfo);

        ResponseRegisterEmployerVO responseUser = new ResponseRegisterEmployerVO(request.getNewUser(), request.getNewEmployer());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


    @PostMapping("users/employee")
    public ResponseEntity<ResponseRegisterEmployeeVO> registerEmployer(@RequestBody RegisterEmployeeRequest request) {
        SignInUserInfo signInUserInfo = modelMapper.map(request.getNewUser(), SignInUserInfo.class);
        RegisterEmployeeInfo registerEmployeeInfo = modelMapper.map(request.getNewEmployee(), RegisterEmployeeInfo.class);

        userService.signInEmployee(signInUserInfo, registerEmployeeInfo);

        ResponseRegisterEmployeeVO responseUser = new ResponseRegisterEmployeeVO(request.getNewUser(), request.getNewEmployee());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users/employer/{employerCode}")
    public ResponseEntity<ResponseFindEmployerVO> getEmployer(@PathVariable("employerCode") String employerCode) {
        UserDTO userDTO = employerService.getByEmployerCode(employerCode);

        ResponseFindEmployerVO findUser = modelMapper.map(userDTO, ResponseFindEmployerVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

    @GetMapping("/users/employee/{employeeCode}")
    public ResponseEntity<ResponseFindEmployeeVO> getEmployee(@PathVariable("employeeCode") String employeeCode) {
        UserDTO userDTO = employerService.getByEmployerCode(employeeCode);

        ResponseFindEmployeeVO findUser = modelMapper.map(userDTO, ResponseFindEmployeeVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

}






