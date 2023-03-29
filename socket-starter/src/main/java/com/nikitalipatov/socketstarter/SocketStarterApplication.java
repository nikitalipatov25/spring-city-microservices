package com.nikitalipatov.socketstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class SocketStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketStarterApplication.class, args);
    }

}
