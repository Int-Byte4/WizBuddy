package com.intbyte.wizbuddy.employeepershop.command.application.controller;

import com.intbyte.wizbuddy.employeepershop.command.application.service.EmployeePerShopService;
import com.intbyte.wizbuddy.employeepershop.command.application.dto.RequestEditEmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.command.infrastructure.service.EmployeePerShopInfraStructureService;
import com.intbyte.wizbuddy.employeepershop.query.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.command.domain.entity.vo.request.RequestInsertEmployeePerShopVO;
import com.intbyte.wizbuddy.employeepershop.command.domain.entity.vo.response.ResponseInsertEmployeePerShopVO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("employeePerShopCommandController")
@RequestMapping("/employeepershop")
public class EmployeePerShopController {

    private final EmployeePerShopService employeePerShopService;
    private final ModelMapper modelMapper;

    public EmployeePerShopController(EmployeePerShopService employeePerShopService, ModelMapper modelMapper) {
        this.employeePerShopService = employeePerShopService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseInsertEmployeePerShopVO> registerEmployeePerShop(@RequestBody RequestInsertEmployeePerShopVO request) {
        EmployeePerShopDTO employeePerShopDTO = modelMapper.map(request, EmployeePerShopDTO.class);

        employeePerShopService.insertEmployeePerShop(employeePerShopDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/modify/employee/{employeeCode}")
    public ResponseEntity<Void> modifyEmployeePerShop(@PathVariable String employeeCode, @RequestParam int shopHourlyWage, @RequestParam int shopMonthlyWage) {
        RequestEditEmployeePerShopDTO info = RequestEditEmployeePerShopDTO.builder()
                .shopHourlyWage(shopHourlyWage)
                .shopMonthlyWage(shopMonthlyWage)
                .build();

        employeePerShopService.editEmployeePerShopById(employeeCode, info);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/employee/{employeeCode}")
    public ResponseEntity<Void> deleteEmployeePerShop(@PathVariable String employeeCode) {
        employeePerShopService.deleteEmployeePerShopByEmployeeCode(employeeCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
