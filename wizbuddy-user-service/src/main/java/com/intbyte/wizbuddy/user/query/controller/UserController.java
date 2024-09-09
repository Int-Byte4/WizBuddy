package com.intbyte.wizbuddy.user.query.controller;

import com.intbyte.wizbuddy.user.query.dto.EmployeeDTO;
import com.intbyte.wizbuddy.user.query.dto.EmployerDTO;
import com.intbyte.wizbuddy.user.query.service.EmployeeService;
import com.intbyte.wizbuddy.user.query.service.EmployerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userQueryController")
@RequestMapping("users")
public class UserController {

    private final Environment env;
    private final EmployerService employerService;
    private final EmployeeService employeeService;

    @Autowired
    public UserController(Environment env, EmployerService employerService, EmployeeService employeeService) {
        this.env = env;
        this.employerService = employerService;
        this.employeeService = employeeService;
    }

    @Operation(summary = "관리자 - 사장 전체 조회")
    @GetMapping("/employers")
    public ResponseEntity<List<EmployerDTO>> getEmployers() {
        List<EmployerDTO> response = employerService.findAllEmployer();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "관리자 - 직원 전체 조회")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<EmployeeDTO> response = employeeService.findAllEmployee();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "사장 단 건 조회")
    @GetMapping("employer/{employerCode}")
    public ResponseEntity<EmployerDTO> getEmployer(@PathVariable("employerCode") String employerCode) {
        EmployerDTO employerQueryDTO = employerService.getByEmployerCode(employerCode);

        return ResponseEntity.status(HttpStatus.OK).body(employerQueryDTO);
    }

    @Operation(summary = "직원 단 건 조회")
    @GetMapping("employee/{employeeCode}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeCode") String employeeCode) {
        EmployeeDTO employeeDTO = employeeService.getByEmployeeCode(employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }
}