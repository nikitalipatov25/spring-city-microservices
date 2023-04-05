package com.nikitalipatov.socketclientstarter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "socket-client-starter")
@Getter
@Setter
public class StarterProperties {

    private String url;
    private String destination;
}
