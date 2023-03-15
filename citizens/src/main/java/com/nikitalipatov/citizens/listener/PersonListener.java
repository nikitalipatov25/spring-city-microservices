package com.nikitalipatov.citizens.listener;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.kafka.CitizenDto;
import com.nikitalipatov.common.dto.kafka.PassportCreateDto;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.kafka.CitizenCreateDto;
import com.nikitalipatov.common.dto.response.PersonDeleteDto;
import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
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
@KafkaListener(topics = "personListener", groupId = "test-gi", containerFactory = "kafkaListenerContainerFactory")
public class PersonListener {

    private final PassportClient passportClient;
    private final CarClient carClient;
    private final HouseClient houseClient;
    private final CitizenService personService;
    private final KafkaTemplate<String, KafkaObject> kafkaTemplate;

    // Если паспорт не создан - удаляем жителя
    @KafkaHandler
    public void personRollbackCreateHandler(PassportCreateDto passportCreateDto) {
        personService.rollbackCitizenCreation(passportCreateDto.getOwnerId());
    }

    @KafkaHandler
    public void personDeleteListener(PersonDeleteDto personDeleteDto) {
        DeletePersonDto personDelete = DeletePersonDto.builder()
                .person(personDeleteDto.getPerson())
                .personDeleteStatus(personDeleteDto.getPersonDeleteStatus())
                .build();
        kafkaTemplate.send("passportEvents", personDelete);
    }

    // отменяем удаление жителя
    @KafkaHandler
    public void personRollbackDeleteHandler(CitizenDto citizenDto) {
        personService.rollbackDeletedCitizen(citizenDto.getCitizenId());
    }

//    @KafkaHandler
//    public void personDeleteHandler(DeletePersonDto deletePerson) {
//
//        if (deletePerson.getPersonDeleteStatus().equals(KafkaStatus.FAIL)) {
//            personService.rollbackDeletedCitizen(deletePerson.getPerson());
//        }
//
//        if (deletePerson.getPersonDeleteStatus().equals(KafkaStatus.FAIL)) {
//            passportClient.rollbackDeletedPassport(deletePerson.getPassport());
//            personService.rollbackDeletedCitizen(deletePerson.getPerson());
//        }
//
//        if (deletePerson.getCarDeleteStatus().equals(KafkaStatus.FAIL)) {
//            carClient.rollbackDeletedPersonCars(deletePerson.getPerson().getPersonId(), deletePerson.getCarList());
//            passportClient.rollbackDeletedPassport(deletePerson.getPassport());
//            personService.rollbackDeletedCitizen(deletePerson.getPerson());
//        }
//
//        if (deletePerson.getHouseDeleteStatus().equals(KafkaStatus.FAIL)) {
//            houseClient.rollbackDeletedCitizenFromHouses(deletePerson.getPerson().getPersonId(), deletePerson.getHouseLst());
//            carClient.rollbackDeletedPersonCars(deletePerson.getPerson().getPersonId(), deletePerson.getCarList());
//            passportClient.rollbackDeletedPassport(deletePerson.getPassport());
//            personService.rollbackDeletedCitizen(deletePerson.getPerson());
//        }
//    }
}
