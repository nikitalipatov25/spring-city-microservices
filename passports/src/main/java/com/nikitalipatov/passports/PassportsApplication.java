package com.nikitalipatov.passports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
public class PassportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportsApplication.class, args);
    }

}
