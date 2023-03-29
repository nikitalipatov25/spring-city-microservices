package com.nikitalipatov.cars.config.websocet;

import com.nikitalipatov.common.logs.LogDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Configuration
public class ClientSessionHandler extends StompSessionHandlerAdapter{

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return LogDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Received : " + ((LogDto) payload).getLogType());
    }
}

