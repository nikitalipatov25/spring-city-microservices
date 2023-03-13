package com.nikitalipatov.cars.kafka;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "carEvents", groupId = "test-gi", containerFactory = "kafkaCarListenerContainerFactory")
public class CarListener {

//    private final CarService carService;
//    private final PassportClient passportClient;
//    private final HouseClient houseClient;

    @KafkaHandler
    public DeletePersonDto handleCarDelete(DeletePersonDto deletePersonDto) {
        return DeletePersonDto.builder()
                .carDeleteStatus(deletePersonDto.getCarDeleteStatus())
                .carList(deletePersonDto.getCarList())
                .build();
    }
}
