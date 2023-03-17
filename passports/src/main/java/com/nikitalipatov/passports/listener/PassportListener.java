package com.nikitalipatov.passports.listener;

import com.nikitalipatov.common.dto.kafka.CitizenEvent;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "citizenCommand", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PassportListener {

    private final PassportService passportService;

    @KafkaHandler
    public void passportHandler(KafkaMessage kafkaMessage) {
        var citizenId = kafkaMessage.getCitizenId();
        switch (kafkaMessage.getEventType()) {
            case CITIZEN_CREATED -> passportService.create(citizenId);
            case CITIZEN_DELETED -> passportService.delete(citizenId);
            case PASSPORT_ROLLBACK -> passportService.rollbackDeletedPassport(citizenId);
        }
    }
}
