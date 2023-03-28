package com.nikitalipatov.citizens.websocket;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class ClientOneDirect {

    public void send(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClientOneDirectSessionHandler clientOneDirectSessionHandler = new ClientOneDirectSessionHandler();
        ListenableFuture<StompSession> sessionAsync =
                stompClient.connect("ws://localhost:8090/websocket-server", clientOneDirectSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/topic/messages", clientOneDirectSessionHandler);
        while (true) {
            session.send("/topic/messages", new IncomingMessage("Direct :: sas " + System.currentTimeMillis()));
            Thread.sleep(2000);
        }
    }


}

class ClientOneDirectSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return IncomingMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Received : " + ((IncomingMessage) payload).getName());
    }
}
