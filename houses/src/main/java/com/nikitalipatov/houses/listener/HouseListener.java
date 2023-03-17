package com.nikitalipatov.houses.listener;

import com.nikitalipatov.common.dto.kafka.CitizenEvent;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "citizenCommand", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class HouseListener {

    private final HouseService houseService;

    @KafkaHandler
    public void houseHandler(KafkaMessage<CitizenEvent> kafkaMessage) {
        switch (kafkaMessage.getEventType()) {
            case CITIZEN_DELETED -> houseService.removePerson(kafkaMessage.getPayload().getCitizenId());
            case HOUSE_ROLLBACK -> houseService.rollbackDeletedCitizenFromHouses(kafkaMessage.getPayload().getCitizenId());
        }
    }
}
