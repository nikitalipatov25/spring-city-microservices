package com.nikitalipatov.citizens.listener;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.kafka.CitizenEvent;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "result", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
@RequiredArgsConstructor
public class CitizenListener {

    private final CitizenService citizenService;

    @KafkaHandler
    public void CitizenStatusHandler(KafkaMessage resultOfEvent) {
        if (resultOfEvent.getStatus().equals(Status.FAIL)) {
            switch (resultOfEvent.getEventType()) {
                case PASSPORT_DELETED, CAR_DELETED, HOUSE_DELETED -> citizenService.rollback(resultOfEvent.getCitizenId(), resultOfEvent.getEventType());
                case PASSPORT_CREATED -> citizenService.rollbackCitizenCreation(resultOfEvent.getCitizenId());
            }
        }
    }
}
