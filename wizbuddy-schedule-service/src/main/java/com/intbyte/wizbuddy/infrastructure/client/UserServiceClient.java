package com.intbyte.wizbuddy.infrastructure.client;

import com.intbyte.wizbuddy.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name="intbyte-user-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping("user-service/users/employee/{employeeCode}")
    ResponseEntity<Map<String, Object>> getEmployee(@PathVariable("employeeCode") String employeeCode);
}
