package com.nikitalipatov.citizens.listener;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.kafka.CitizenCreateDto;
import com.nikitalipatov.common.dto.kafka.CitizenDeleteDto;
import com.nikitalipatov.common.kafka.KafkaObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "citizenSender", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PersonSender {

    private final KafkaTemplate<String, KafkaObject> kafkaTemplate;

    //Сообщаем о том, что житель создан и ему нужен паспорт
    @KafkaHandler
    public void citizenCreateHandler(CitizenCreateDto citizenCreateDto) {
        kafkaTemplate.send("passportListener", citizenCreateDto);
    }

    // Сообщаем о том, что житель удален и необходимо удалить все данные связанные с ним
    @KafkaHandler
    public void citizenDeleteHandler(CitizenDeleteDto citizenDeleteDto) {
        kafkaTemplate.send("passportListener", citizenDeleteDto);
    }
}
