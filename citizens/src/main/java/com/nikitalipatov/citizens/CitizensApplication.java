package com.nikitalipatov.citizens;

import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import com.nikitalipatov.common.kafka.KafkaConsumerConfig;
import com.nikitalipatov.common.kafka.KafkaProducerConfig;
import com.nikitalipatov.common.websocket.WebSocketClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({ KafkaConsumerConfig.class, KafkaProducerConfig.class})
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableFeignClients(clients = {PassportClient.class, HouseClient.class, CarClient.class})
public class CitizensApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitizensApplication.class, args);
    }
}
