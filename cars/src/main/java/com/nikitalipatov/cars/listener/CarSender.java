package com.nikitalipatov.cars.listener;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.CarDeleteStatus;
import com.nikitalipatov.common.enums.KafkaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "carEvents", groupId = "test-gi", containerFactory = "kafkaCarListenerContainerFactory")
public class CarSender {

    private final CarService carService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

//    продюсера кафки лучше отделить от консьюмера.
//
//    листенер читает сообщение
//    листенер вызывает сервисный метод который что то делает
//    Сервисный метод выполняет логику и отправляет событие в кафку

    @KafkaHandler
    public void carStatusHandler(CarDeleteStatus carDeleteStatus) {
        if (carDeleteStatus.getCarDeleteStatus().equals(KafkaStatus.SUCCESS)) {
            kafkaTemplate.send("houseEvents", deletePersonDto);
        }
    }
}
