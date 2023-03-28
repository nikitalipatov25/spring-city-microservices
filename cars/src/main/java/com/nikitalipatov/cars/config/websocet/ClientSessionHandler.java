package com.nikitalipatov.cars.config.websocet;

import com.nikitalipatov.common.logs.MyLog;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Configuration
public class ClientSessionHandler extends StompSessionHandlerAdapter{

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MyLog.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Received : " + ((MyLog) payload).getLogType());
    }
}

