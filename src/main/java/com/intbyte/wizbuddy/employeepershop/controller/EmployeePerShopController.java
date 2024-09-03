package com.intbyte.wizbuddy.employeepershop.controller;

import com.intbyte.wizbuddy.employeepershop.domain.EditEmployeePerShopInfo;
import com.intbyte.wizbuddy.employeepershop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.employeepershop.service.EmployeePerShopService;
import com.intbyte.wizbuddy.employeepershop.vo.request.RequestInsertEmployeePerShopVO;
import com.intbyte.wizbuddy.employeepershop.vo.request.RequestModifyEmployeePerShopVO;
import com.intbyte.wizbuddy.employeepershop.vo.response.ResponseInsertEmployeePerShopVO;
import com.intbyte.wizbuddy.shop.dto.ShopDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeepershop")
public class EmployeePerShopController {

    private final EmployeePerShopService employeePerShopService;
    private final ModelMapper modelMapper;

    public EmployeePerShopController(EmployeePerShopService employeePerShopService, ModelMapper modelMapper) {
        this.employeePerShopService = employeePerShopService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register/employer/{employerCode}")
    public ResponseEntity<ResponseInsertEmployeePerShopVO> registerEmployeePerShop(@PathVariable String employerCode ,@RequestBody RequestInsertEmployeePerShopVO request) {
        EmployeePerShopDTO employeePerShopDTO = modelMapper.map(request, EmployeePerShopDTO.class);

        employeePerShopService.insertEmployeePerShop(employerCode, employeePerShopDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("list")
    public ResponseEntity<List<EmployeePerShopDTO>> getEmployeesPerShop() {
        List<EmployeePerShopDTO> employeePerShopDTOList = employeePerShopService.findAllEmployeePerShop();

        return ResponseEntity.status(HttpStatus.OK).body(employeePerShopDTOList);
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<EmployeePerShopDTO> getEmployeePerShop(@PathVariable String employeeCode) {
        EmployeePerShopDTO employeePerShopDTO = employeePerShopService.findEmployeePerShopById(employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(employeePerShopDTO);
    }

    // 직원이 속한 매장 조회
    @GetMapping("/employee/{employeeCode}")
    public ResponseEntity<ShopDTO> getShopByEmployeeCode(@PathVariable String employeeCode) {
        ShopDTO shopDTO = employeePerShopService.getShopByEmployeeCode(employeeCode);

        return ResponseEntity.status(HttpStatus.OK).body(shopDTO);
    }

    @PatchMapping("/modify/shop/{shopCode}/employee/{employeeCode}")
    public ResponseEntity<Void> modifyEmployeePerShop(@PathVariable int shopCode, @PathVariable String employeeCode, @RequestBody RequestModifyEmployeePerShopVO request) {
        EditEmployeePerShopInfo info = new EditEmployeePerShopInfo(request.getShopHourlyWage(), request.getShopMonthlyWage());

        employeePerShopService.editEmployeePerShopById(shopCode, employeeCode, info);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/employer/{employerCode}/employee/{employeeCode}")
    public ResponseEntity<Void> deleteEmployeePerShop(@PathVariable String employerCode, @PathVariable String employeeCode) {
        employeePerShopService.deleteEmployeePerShopByEmployerCode(employerCode, employeeCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
