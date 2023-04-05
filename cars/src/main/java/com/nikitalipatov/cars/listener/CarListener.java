package com.nikitalipatov.cars.listener;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.CitizenEvent;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "citizenCommand", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class CarListener {

    private final CarService carService;

    @KafkaHandler
    public void carHandler(KafkaMessage kafkaMessage) {
        switch (kafkaMessage.getEventType()) {
            case CITIZEN_DELETED -> carService.deletePersonCars(kafkaMessage.getCitizenId());
            case CAR_ROLLBACK -> carService.rollbackDeletedPersonCars(kafkaMessage.getCitizenId());
        }
    }
}
