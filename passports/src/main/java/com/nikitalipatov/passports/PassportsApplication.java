package com.nikitalipatov.passports;

import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.common.feign.PassportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = {PassportClient.class, CitizenClient.class})
public class PassportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportsApplication.class, args);
    }

}
