package com.intbyte.wizbuddy.board.command.infrastructure.client;

import com.intbyte.wizbuddy.board.command.infrastructure.dto.ShopDTO;
import com.intbyte.wizbuddy.comment.command.infrastructure.dto.EmployeePerShopDTO;
import com.intbyte.wizbuddy.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="intbyte-shop-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface ShopServiceClient {

    @GetMapping("/shop-service/shop/{shopCode}")
    ShopDTO getShop(@PathVariable("shopCode") int shopCode);

    @GetMapping("/shop-service/employeepershop/shop/{shopCode}/employee/{employeeCode}")
    ResponseEntity<EmployeePerShopDTO> getShopByEmployeeCode(@PathVariable("shopCode") int shopCode, @PathVariable("employeeCode") String employeeCode);
}
