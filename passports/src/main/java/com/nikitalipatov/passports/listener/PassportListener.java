package com.nikitalipatov.passports.listener;

import com.nikitalipatov.common.dto.kafka.CitizenDeleteDto;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.kafka.CitizenCreateDto;
import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "passportListener", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PassportListener {

    private final PassportService passportService;

    // Получаем сообщение о том, что житель создан
    @KafkaHandler
    public void passportCreateHandler(CitizenCreateDto citizenCreateDto) {
        passportService.create(citizenCreateDto.getCitizenId());
    }

    // Получаем сообщение о том, что необходимо удалить паспорт
    @KafkaHandler
    public void passportDeleteHandler(CitizenDeleteDto citizenDeleteDto) {
        passportService.delete(citizenDeleteDto.getCitizen().getPersonId());
    }

    // отменяем удаление паспорта
    @KafkaHandler
    public void passportRollbackDeleteHandler(int ownerId) {
        passportService.rollbackDeletedPassport(ownerId);
    }
}
