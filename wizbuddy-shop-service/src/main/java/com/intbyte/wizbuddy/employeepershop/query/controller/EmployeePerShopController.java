package com.intbyte.wizbuddy.employeepershop.query.controller;

import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.query.service.EmployeePerShopService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("employeePerShopQueryController")
@RequestMapping("/employeepershop")
public class EmployeePerShopController {

    private final EmployeePerShopService employeePerShopService;
    private final ModelMapper modelMapper;

    public EmployeePerShopController(EmployeePerShopService employeePerShopService, ModelMapper modelMapper) {
        this.employeePerShopService = employeePerShopService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("list")
    public ResponseEntity<List<EmployeePerShopDTO>> getEmployeesPerShop() {
        List<EmployeePerShopDTO> employeePerShopDTOList = employeePerShopService.findAllEmployeePerShop();

        return ResponseEntity.status(HttpStatus.OK).body(employeePerShopDTOList);
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<List<EmployeePerShopDTO>> getShopsbyEmployeeCode(@PathVariable String employeeCode) {
        List<EmployeePerShopDTO> employeePerShopDTO = employeePerShopService.findEmployeePerShopById(employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(employeePerShopDTO);
    }

    @GetMapping("shop/{shopCode}/employee/{employeeCode}")
    public ResponseEntity<EmployeePerShopDTO> getShopByEmployeeCode(@PathVariable int shopCode, @PathVariable String employeeCode) {
        EmployeePerShopDTO response = employeePerShopService.getEmployeePerShopByEmployeeCode(shopCode, employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
