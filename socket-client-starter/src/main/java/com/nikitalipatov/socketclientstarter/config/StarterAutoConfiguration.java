package com.nikitalipatov.socketclientstarter.config;

import com.nikitalipatov.socketclientstarter.config.websocket.StompSessionConfig;
import com.nikitalipatov.socketclientstarter.service.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarterProperties.class)
@RequiredArgsConstructor
public class StarterAutoConfiguration {

    @Bean
    StompSessionConfig stompSessionConfig(StarterProperties starterProperties) {
        return new StompSessionConfig(starterProperties);
    }

    @Bean
    public SenderService senderService(StompSessionConfig stompSessionConfig) {
        return new SenderService(stompSessionConfig);
    }
}
