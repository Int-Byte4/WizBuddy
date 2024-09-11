package com.intbyte.wizbuddy.task.command.infrastructure.client;

import com.intbyte.wizbuddy.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="intbyte-shop-service", url="localhost:8000", configuration = FeignClientConfig.class)
public interface ShopServiceClient {

}
