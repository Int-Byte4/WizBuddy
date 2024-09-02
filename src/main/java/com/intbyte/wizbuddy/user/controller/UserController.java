package com.intbyte.wizbuddy.user.controller;

import com.intbyte.wizbuddy.security.JwtUtil;
import com.intbyte.wizbuddy.user.domain.*;
import com.intbyte.wizbuddy.user.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.service.EmployeeService;
import com.intbyte.wizbuddy.user.service.EmployerService;
import com.intbyte.wizbuddy.user.service.UserService;
import com.intbyte.wizbuddy.user.vo.request.*;
import com.intbyte.wizbuddy.user.vo.response.ResponseEditEmployeeVO;
import com.intbyte.wizbuddy.user.vo.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @PostMapping("/employer")
    public ResponseEntity<ResponseRegisterEmployerVO> registerEmployer(@RequestBody RegisterEmployerRequest request) {
        SignInUserInfo signInUserInfo = modelMapper.map(request.getNewUser(), SignInUserInfo.class);
        RegisterEmployerInfo registerEmployerInfo = modelMapper.map(request.getNewEmployer(), RegisterEmployerInfo.class);

        ResponseRegisterEmployerVO responseUser = userService.signInEmployer(signInUserInfo, registerEmployerInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @PostMapping("/employee")
    public ResponseEntity<ResponseRegisterEmployeeVO> registerEmployee(@RequestBody RegisterEmployeeRequest request) {
        SignInUserInfo signInUserInfo = modelMapper.map(request.getNewUser(), SignInUserInfo.class);
        RegisterEmployeeInfo registerEmployeeInfo = modelMapper.map(request.getNewEmployee(), RegisterEmployeeInfo.class);

        ResponseRegisterEmployeeVO responseUser =  userService.signInEmployee(signInUserInfo, registerEmployeeInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/employer/{employerCode}")
    public ResponseEntity<ResponseFindEmployerVO> getEmployer(@PathVariable("employerCode") String employerCode) {
        EmployerDTO employerDTO = employerService.getByEmployerCode(employerCode);

        ResponseFindEmployerVO findUser = modelMapper.map(employerDTO, ResponseFindEmployerVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

    @GetMapping("/employee/{employeeCode}")
    public ResponseEntity<ResponseFindEmployeeVO> getEmployee(@PathVariable("employeeCode") String employeeCode) {
        EmployeeDTO employeeDTO = employeeService.getByEmployeeCode(employeeCode);

        ResponseFindEmployeeVO findUser = modelMapper.map(employeeDTO, ResponseFindEmployeeVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUser);
    }

    @PatchMapping("/employer/edit")
    public ResponseEntity<ResponseEditEmployerVO> modifyEmployer(@RequestBody RequestEditEmployerVO request) {
        EditEmployerInfo editEmployerInfo = modelMapper.map(request, EditEmployerInfo.class);

        ResponseEditEmployerVO responseUser =  employerService.modifyEmployer(editEmployerInfo);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PatchMapping("/employee/edit")
    public ResponseEntity<ResponseEditEmployeeVO> modifyEmployer(@RequestBody RequestEditEmployeeVO request) {
        EditEmployeeInfo editEmployeeInfo = modelMapper.map(request, EditEmployeeInfo.class);

        ResponseEditEmployeeVO responseUser =  employeeService.modifyEmployee(editEmployeeInfo);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PatchMapping("/employer/delete")
    public ResponseEntity<ResponseDeleteEmployerVO> deleteEmployer(@RequestBody RequestDeleteEmployerVO request) {
        DeleteEmployerInfo deleteEmployerInfo = modelMapper.map(request, DeleteEmployerInfo.class);

        ResponseDeleteEmployerVO responseUser =  employerService.deleteEmployer(deleteEmployerInfo);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PatchMapping("/employee/delete")
    public ResponseEntity<ResponseDeleteEmployeeVO> deleteEmployer(@RequestBody RequestDeleteEmployeeVO request) {
        DeleteEmployeeInfo deleteEmployeeInfo = modelMapper.map(request, DeleteEmployeeInfo.class);

        ResponseDeleteEmployeeVO responseUser =  employeeService.deleteEmployee(deleteEmployeeInfo);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }
}