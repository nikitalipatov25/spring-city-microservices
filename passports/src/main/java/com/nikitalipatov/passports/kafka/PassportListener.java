package com.nikitalipatov.passports.kafka;

import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.PersonCreationDto;
import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "passportEvents", groupId = "test-gi", containerFactory = "kafkaPassportListenerContainerFactory")
public class PassportListener {

    private final CitizenClient citizenClient;
    private final PassportService passportService;
    private final CarClient carClient;

    @KafkaHandler
    public void listenPassport(PersonCreationDto personCreationDto) {
        if (personCreationDto.getStatus().equals("not ok")) {
            passportService.delete(personCreationDto.getPersonId());
            citizenClient.rollbackCitizenCreation(personCreationDto.getPersonId());
        }
    }

    @KafkaHandler
    public void handlePassportDelete(DeletePersonDto deletePersonDto) {
        if (deletePersonDto.getPassportDeleteStatus().equals("not ok")) {
            passportService.rollback(deletePersonDto.getPassport());
        } else {
            carClient.deletePersonCars(deletePersonDto.getPerson().getPersonId());
        }
    }
}
