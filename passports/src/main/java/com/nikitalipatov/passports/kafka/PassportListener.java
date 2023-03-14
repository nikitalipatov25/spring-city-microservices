package com.nikitalipatov.passports.kafka;

import com.nikitalipatov.common.dto.request.DeleteStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.PersonCreationDto;
import com.nikitalipatov.common.feign.CarClient;
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
@KafkaListener(topics = "passportEvents", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PassportListener {

    private final CitizenClient citizenClient;
    private final PassportService passportService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaHandler
    public void listenPassport(PersonCreationDto personCreationDto) {
        if (personCreationDto.getStatus().equals("not ok")) {
            passportService.delete(personCreationDto.getPersonId());
            citizenClient.rollbackCitizenCreation(personCreationDto.getPersonId());
        }
    }

    @KafkaHandler
    @Transactional
    public void handlePassportDelete(DeletePersonDto deletePersonDto) {
        var passport = passportService.getByOwnerId(deletePersonDto.getPerson().getPersonId());
        try {
            passportService.delete(deletePersonDto.getPerson().getPersonId());
            deletePersonDto.setPassport(passport);
            deletePersonDto.setPassportDeleteStatus(DeleteStatus.SUCCESS);
            kafkaTemplate.send("carEvents", deletePersonDto);
        } catch (Exception e) {
            e.printStackTrace();
            deletePersonDto.setPassport(passport);
            deletePersonDto.setPassportDeleteStatus(DeleteStatus.SUCCESS);
            kafkaTemplate.send("personEvents", deletePersonDto);
        }
    }
}
