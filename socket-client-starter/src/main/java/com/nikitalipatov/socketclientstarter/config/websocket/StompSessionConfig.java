package com.nikitalipatov.socketclientstarter.config.websocket;

import com.nikitalipatov.socketclientstarter.config.StarterProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
@RequiredArgsConstructor
public class StompSessionConfig {

    private final StarterProperties starterProperties;

    @Bean
    @SneakyThrows
    public StompSession stompSession() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ClientSessionHandler clientSessionHandler = new ClientSessionHandler();
        ListenableFuture<StompSession> sessionAsync =
                stompClient.connect(starterProperties.getUrl(), clientSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe(starterProperties.getDestination(), clientSessionHandler);
        return session;
    }

}
