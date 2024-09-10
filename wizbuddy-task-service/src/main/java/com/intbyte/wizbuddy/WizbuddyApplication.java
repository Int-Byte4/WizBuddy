package com.intbyte.wizbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WizbuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WizbuddyApplication.class, args);
    }

}
