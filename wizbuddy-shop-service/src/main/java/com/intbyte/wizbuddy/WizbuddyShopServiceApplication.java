package com.intbyte.wizbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WizbuddyShopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WizbuddyShopServiceApplication.class, args);
    }

}
