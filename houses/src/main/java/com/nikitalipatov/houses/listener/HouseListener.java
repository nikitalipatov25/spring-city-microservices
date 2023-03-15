package com.nikitalipatov.houses.listener;

import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.houses.service.HouseService;
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
@KafkaListener(topics = "houseEvents", groupId = "test-gi", containerFactory = "kafkaHouseListenerContainerFactory")
public class HouseListener {

    private final HouseService houseService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaHandler
    @Transactional
    public void houseDeleteHandler(DeletePersonDto deletePersonDto) {
       var personHouses = houseService.getPersonHouses(deletePersonDto.getPerson().getPersonId());
       try {
           houseService.removePerson(deletePersonDto.getPerson().getPersonId());
           deletePersonDto.setHouseDeleteStatus(KafkaStatus.SUCCESS);
           deletePersonDto.setHouseLst(personHouses);
           //kafkaTemplate.send("personEvents", deletePersonDto);
       } catch (Exception e) {
           e.printStackTrace();
           deletePersonDto.setHouseDeleteStatus(KafkaStatus.FAIL);
           deletePersonDto.setHouseLst(personHouses);
           kafkaTemplate.send("error", deletePersonDto);
       }
    }
}
