package com.intbyte.wizbuddy.employeepershop.query.controller;

import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.query.service.EmployeePerShopService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("employeePerShopQueryController")
@RequestMapping("/employeepershop")
public class EmployeePerShopController {

    private final EmployeePerShopService employeePerShopService;

    public EmployeePerShopController(EmployeePerShopService employeePerShopService, ModelMapper modelMapper) {
        this.employeePerShopService = employeePerShopService;
    }

    @Operation(summary = "관리자 - 매장 별 직원 리스트 전체 조회")
    @GetMapping("list")
    public ResponseEntity<List<EmployeePerShopDTO>> getEmployeesPerShop() {
        List<EmployeePerShopDTO> employeePerShopDTOList = employeePerShopService.findAllEmployeePerShop();

        return ResponseEntity.status(HttpStatus.OK).body(employeePerShopDTOList);
    }

    @Operation(summary = "직원 - 직원이 속한 매장 리스트 조회")
    @GetMapping("/{employeeCode}")
    public ResponseEntity<List<EmployeePerShopDTO>> getShopsbyEmployeeCode(@PathVariable String employeeCode) {
        List<EmployeePerShopDTO> employeePerShopDTO = employeePerShopService.findEmployeePerShopById(employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(employeePerShopDTO);
    }

    @Operation(summary = "사장 - 매장에 속한 특정 직원 조회")
    @GetMapping("shop/{shopCode}/employee/{employeeCode}")
    public ResponseEntity<EmployeePerShopDTO> getShopByEmployeeCode(@PathVariable int shopCode, @PathVariable String employeeCode) {
        EmployeePerShopDTO response = employeePerShopService.getEmployeePerShopByEmployeeCode(shopCode, employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
