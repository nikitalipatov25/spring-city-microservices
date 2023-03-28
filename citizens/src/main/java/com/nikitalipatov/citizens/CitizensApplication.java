package com.nikitalipatov.citizens;

import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@EnableFeignClients(clients = {PassportClient.class, HouseClient.class, CarClient.class})
public class CitizensApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitizensApplication.class, args);
    }
}
