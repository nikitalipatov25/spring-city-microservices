package com.nikitalipatov.citizens.kafka;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.request.KafkaStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.PersonCreationDto;
import com.nikitalipatov.common.dto.response.PersonDeleteDto;
import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "personEvents", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PersonListener {

    private final PassportClient passportClient;
    private final CarClient carClient;
    private final HouseClient houseClient;
    private final CitizenService personService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaHandler
    public void handlePersonCreation(PersonCreationDto personCreationDto) {
        if (personCreationDto.getStatus().equals(KafkaStatus.SUCCESS)) {
            passportClient.create(personCreationDto.getPersonId());
        }
    }

    @KafkaHandler
    public void listenToPersonDelete(PersonDeleteDto personDeleteDto) {
        DeletePersonDto personDelete = DeletePersonDto.builder()
                .person(personDeleteDto.getPerson())
                .personDeleteStatus(personDeleteDto.getPersonDeleteStatus())
                .build();
        kafkaTemplate.send("passportEvents", personDelete);
    }

    @KafkaHandler
    public void handlePersonDelete(DeletePersonDto deletePerson) {

        if (deletePerson.getPersonDeleteStatus().equals(KafkaStatus.FAIL)) {
            personService.rollback(deletePerson.getPerson());
        }

        if (deletePerson.getPersonDeleteStatus().equals(KafkaStatus.FAIL)) {
            passportClient.rollback(deletePerson.getPassport());
            personService.rollback(deletePerson.getPerson());
        }

        if (deletePerson.getCarDeleteStatus().equals(KafkaStatus.FAIL)) {
            carClient.rollback(deletePerson.getPerson().getPersonId(), deletePerson.getCarList());
            passportClient.rollback(deletePerson.getPassport());
            personService.rollback(deletePerson.getPerson());
        }

        if (deletePerson.getHouseDeleteStatus().equals(KafkaStatus.FAIL)) {
            houseClient.rollback(deletePerson.getPerson().getPersonId(), deletePerson.getHouseLst());
            carClient.rollback(deletePerson.getPerson().getPersonId(), deletePerson.getCarList());
            passportClient.rollback(deletePerson.getPassport());
            personService.rollback(deletePerson.getPerson());
        }
    }
}
