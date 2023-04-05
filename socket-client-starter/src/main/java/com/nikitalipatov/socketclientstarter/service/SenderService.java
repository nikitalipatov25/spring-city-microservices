package com.nikitalipatov.socketclientstarter.service;

import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.socketclientstarter.config.websocket.StompSessionConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class SenderService {

    private final StompSessionConfig stompSessionConfig;

    @SneakyThrows
    public void send(String topic, LogDto payload) {
        stompSessionConfig.stompSession().send(topic, payload);
    }
}
