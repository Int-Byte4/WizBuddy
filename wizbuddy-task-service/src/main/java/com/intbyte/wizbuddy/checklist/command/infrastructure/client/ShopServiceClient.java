package com.intbyte.wizbuddy.checklist.command.infrastructure.client;

import com.intbyte.wizbuddy.checklist.command.infrastructure.dto.ShopDTO;
import com.intbyte.wizbuddy.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="intbyte-shop-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface ShopServiceClient {

    @GetMapping("/{shopCode}")
    ResponseEntity<ShopDTO> getShop(@PathVariable int shopCode);
}
