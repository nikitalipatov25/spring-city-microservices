package com.nikitalipatov.houses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HousesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousesApplication.class, args);
    }

}
