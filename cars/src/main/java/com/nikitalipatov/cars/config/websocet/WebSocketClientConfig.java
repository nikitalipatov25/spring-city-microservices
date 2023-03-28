package com.nikitalipatov.cars.config.websocet;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
public class WebSocketClientConfig {

    @Bean
    public StompSession stompSession() throws InterruptedException, ExecutionException{

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ClientSessionHandler clientSessionHandler = new ClientSessionHandler();
        ListenableFuture<StompSession> sessionAsync =
                stompClient.connect("ws://localhost:8090/websocket-server", clientSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/topic/logs", clientSessionHandler);
        return session;
    }
}
