package com.nikitalipatov.cars;

import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.common.kafka.KafkaConsumerConfig;
import com.nikitalipatov.common.kafka.KafkaProducerConfig;
import com.nikitalipatov.common.websocket.WebSocketClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({KafkaConsumerConfig.class, KafkaProducerConfig.class})
@SpringBootApplication
@EnableFeignClients(clients = {CitizenClient.class})
public class CarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsApplication.class, args);
    }

}
