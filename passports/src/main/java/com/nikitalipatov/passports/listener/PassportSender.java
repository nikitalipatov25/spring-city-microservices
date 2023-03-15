package com.nikitalipatov.passports.listener;

import com.nikitalipatov.common.dto.kafka.CitizenDto;
import com.nikitalipatov.common.dto.kafka.PassportCreateDto;
import com.nikitalipatov.common.dto.kafka.PassportDeleteStatus;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.common.kafka.KafkaObject;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "passportSender", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PassportSender {

    private final KafkaTemplate<String, KafkaObject> kafkaTemplate;

    // Получаем сообщение о статусе создания паспорта, если неудачно - просим удалить жителя
    @KafkaHandler
    public void passportCreateHandler(PassportCreateDto passportCreateDto) {
        if (passportCreateDto.getPassportCreateStatus().equals(KafkaStatus.FAIL)) {
            kafkaTemplate.send("citizenListener", passportCreateDto);
        }
    }

    // Получаем сообщение о статусе удаления паспорта, если неудачно - откат жителя, если удачно - производим удаление далее
    @KafkaHandler
    public void passportDeleteStatusHandler(PassportDeleteStatus passportDeleteStatus) {
        if (passportDeleteStatus.getPassportDeleteStatus().equals(KafkaStatus.FAIL)) {
            kafkaTemplate.send("citizenListener", passportDeleteStatus);
        } else {
            kafkaTemplate.send("carListener", CitizenDto.builder()
                    .citizenId(passportDeleteStatus.getPassport().getOwnerId())
                    .build());
        }
    }
}
