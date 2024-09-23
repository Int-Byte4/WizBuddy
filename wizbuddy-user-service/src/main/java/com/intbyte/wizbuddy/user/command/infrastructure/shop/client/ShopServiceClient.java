package com.intbyte.wizbuddy.user.command.infrastructure.shop.client;

import com.intbyte.wizbuddy.user.command.infrastructure.shop.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.dto.ShopDTO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.request.RequestDeleteShopVO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.request.RequestEditShopVO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.request.RequestInsertEmployeePerShopVO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.request.RequestRegisterShopVO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.response.ResponseEditShopVO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.response.ResponseInsertEmployeePerShopVO;
import com.intbyte.wizbuddy.user.command.infrastructure.shop.vo.response.ResponseRegisterShopVO;
import com.intbyte.wizbuddy.user.config.FeignClientConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="intbyte-shop-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface ShopServiceClient {

    @Operation(summary = "사장 - 매장 등록")
    @PostMapping("/shop-service/shop/register/{employerCode}")
    ResponseRegisterShopVO registerShop(@PathVariable String employerCode, @RequestBody RequestRegisterShopVO request);

    @Operation(summary = "관리자 - 매장 전체 조회")
    @GetMapping("/shop-service/shop/shops")
    List<ShopDTO> getShops();

    @Operation(summary = "매장 단 건 조회")
    @GetMapping("/shop-service/shop/{shopCode}")
    ShopDTO getShop(@PathVariable int shopCode);

    @Operation(summary = "사장 - 매장 정보 수정")
    @PatchMapping("/shop-service/shop/edit/{employerCode}")
    ResponseEditShopVO modifyShop(@PathVariable String employerCode, @RequestBody RequestEditShopVO request);

    @Operation(summary = "사장 - 매장 삭제")
    @PatchMapping("/shop-service/shop/delete/{employerCode}")
    ResponseEntity<Void> deleteShop(@PathVariable String employerCode, @RequestBody RequestDeleteShopVO request);

    @Operation(summary = "매장 별 직원 등록")
    @PostMapping("/shop-service/employeepershop/register")
    ResponseEntity<ResponseInsertEmployeePerShopVO> registerEmployeePerShop(@RequestBody RequestInsertEmployeePerShopVO request);

    @Operation(summary = "관리자 - 매장 별 직원 전체 조회")
    @GetMapping("/shop-service/employeepershop/list")
    ResponseEntity<List<EmployeePerShopDTO>> getEmployeesPerShop();

    @Operation(summary = "직원 - 직원이 속한 매장 리스트 조회")
    @GetMapping("/shop-service/employeepershop/{employeeCode}")
    ResponseEntity<List<EmployeePerShopDTO>> getEmployeeIncludeShops(@PathVariable String employeeCode);

    @Operation(summary = "사장 - 매장에 속한 특정 직원 조회")
    @GetMapping("/shop-service/employeepershop/shop/{shopCode}/employee/{employeeCode}")
    ResponseEntity<EmployeePerShopDTO> getShopByEmployeeCode(@PathVariable int shopCode, @PathVariable String employeeCode);

    @Operation(summary = "관리자, 사장 - 매장 별 직원 정보 수정")
    @PatchMapping("/shop-service/employeepershop/modify/employee/{employeeCode}")
    ResponseEntity<Void> modifyEmployeePerShop(@PathVariable String employeeCode, @RequestParam int shopHourlyWage, @RequestParam int shopMonthlyWage);

    @Operation(summary = "사장 - 매장 별 직원 삭제")
    @DeleteMapping("/shop-service/employeepershop/delete/employee/{employeeCode}")
    ResponseEntity<Void> deleteEmployeePerShop(@PathVariable String employeeCode);
}
