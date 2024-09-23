package com.intbyte.wizbuddy.shop.command.infrastructure.client;

import com.intbyte.wizbuddy.config.FeignClientConfig;
import com.intbyte.wizbuddy.shop.command.infrastructure.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="intbyte-user-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("employer/{employerCode}")
    ResponseEntity<UserDTO> getEmployer(@PathVariable("employerCode") String employerCode);
}
