package com.nikitalipatov.houses;

import com.nikitalipatov.common.kafka.KafkaConsumerConfig;
import com.nikitalipatov.common.kafka.KafkaProducerConfig;
import com.nikitalipatov.common.websocket.WebSocketClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({WebSocketClientConfig.class, KafkaConsumerConfig.class, KafkaProducerConfig.class})
@SpringBootApplication
@EnableFeignClients
public class HousesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousesApplication.class, args);
    }

}
