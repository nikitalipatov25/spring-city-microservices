package com.nikitalipatov.common.websocket;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
public class WebSocketClientConfig {

    @Value("${websocket.url}")
    private String url;

    @Value("${websocket.destination}")
    private String destination;

    @Bean
    @SneakyThrows
    public StompSession stompSession() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ClientSessionHandler clientSessionHandler = new ClientSessionHandler();
        ListenableFuture<StompSession> sessionAsync =
                stompClient.connect(url, clientSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe(destination, clientSessionHandler);
        return session;
    }
}
