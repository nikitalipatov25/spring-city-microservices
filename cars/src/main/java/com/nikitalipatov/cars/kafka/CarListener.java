package com.nikitalipatov.cars.kafka;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.request.DeleteStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
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
@KafkaListener(topics = "carEvents", groupId = "test-gi", containerFactory = "kafkaCarListenerContainerFactory")
public class CarListener {

    private final CarService carService;
    private final KafkaTemplate<String, DeletePersonDto> kafkaTemplate;

    @KafkaHandler
    @Transactional
    public void handleCarDelete(DeletePersonDto deletePersonDto) {
        var personCars = carService.getCitizenCar(deletePersonDto.getPerson().getPersonId());
        try {
            carService.deletePersonCars(deletePersonDto.getPerson().getPersonId());
            deletePersonDto.setCarList(personCars);
            deletePersonDto.setCarDeleteStatus(DeleteStatus.SUCCESS);
            kafkaTemplate.send("houseEvents", deletePersonDto);
        } catch (Exception e) {
            deletePersonDto.setCarList(personCars);
            deletePersonDto.setCarDeleteStatus(DeleteStatus.FAIL);
            kafkaTemplate.send("personEvents", deletePersonDto);
        }
    }
}
