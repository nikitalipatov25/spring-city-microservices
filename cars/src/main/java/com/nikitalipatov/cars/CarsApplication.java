package com.nikitalipatov.cars;

import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = {CitizenClient.class})
public class CarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsApplication.class, args);
    }

}
