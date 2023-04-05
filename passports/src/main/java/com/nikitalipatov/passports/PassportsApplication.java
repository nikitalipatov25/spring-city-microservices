package com.nikitalipatov.passports;

import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.common.feign.PassportClient;
import com.nikitalipatov.common.kafka.KafkaConsumerConfig;
import com.nikitalipatov.common.kafka.KafkaProducerConfig;
import com.nikitalipatov.common.websocket.WebSocketClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({WebSocketClientConfig.class, KafkaConsumerConfig.class, KafkaProducerConfig.class})
@SpringBootApplication
@EnableFeignClients(clients = {PassportClient.class, CitizenClient.class})
public class PassportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportsApplication.class, args);
    }

}
