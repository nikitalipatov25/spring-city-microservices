package com.nikitalipatov.citizens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
public class CitizensApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitizensApplication.class, args);
    }

}
