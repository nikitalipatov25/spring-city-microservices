package com.nikitalipatov.citizens.kafka;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.PersonCreationDto;
import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "personEvents", groupId = "test-gi", containerFactory = "kafkaPersonListenerContainerFactory")
public class PersonListener {

    private final PassportClient passportClient;
    private final CarClient carClient;
    private final HouseClient houseClient;
    private final CitizenService personService;

    @KafkaHandler
    public void handlePersonCreation(PersonCreationDto personCreationDto) {
        if (personCreationDto.getStatus().equals("ok")) {
            passportClient.create(personCreationDto.getPersonId());
        }
    }

    @KafkaHandler
    public void handlePersonDelete(DeletePersonDto deletePerson) {

        if (deletePerson.getPassportDeleteStatus().equals("not ok") &&
                deletePerson.getCarDeleteStatus().equals("not ok") &&
                deletePerson.getHouseDeleteStatus().equals("not ok")) {
            passportClient.rollback();
            carClient.rollback();
            houseClient.rollback();
            personService.rollback();
        }
        if (deletePerson.getCarDeleteStatus().equals("not ok") && deletePerson.getHouseDeleteStatus().equals("not ok")) {
            carClient.rollback();
            houseClient.rollback();
            personService.rollback();
        }
        if (deletePerson.getHouseDeleteStatus().equals("not ok")) {
            houseClient.rollback();
            personService.rollback();
        }
    }
}
