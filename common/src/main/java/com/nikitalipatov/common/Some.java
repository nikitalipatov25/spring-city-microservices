package com.nikitalipatov.common;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component
public class Some {

    private final StompSession stompSession;

    public void sendSome(String dest, Object obj) {
        stompSession.send(dest, obj);
    }
}
