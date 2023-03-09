package com.nikitalipatov.passports.kafka;

import com.nikitalipatov.common.dto.kafka.PersonCreationDto;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "baeldung", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PassportListener {

    private final PassportService passportService;

    @KafkaHandler
    public void listenGroupFoo(PersonCreationDto personCreationDto) {

        if (personCreationDto.getStatus().equals("Success")) {
            passportService.create(personCreationDto.getPersonId());
        } else {
            throw new RuntimeException();
        }
    }
}
