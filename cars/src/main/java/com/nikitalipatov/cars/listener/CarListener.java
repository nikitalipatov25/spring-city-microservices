package com.nikitalipatov.cars.listener;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.CarDeleteStatus;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
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

//    продюсера кафки лучше отделить от консьюмера.
//
//    листенер читает сообщение
//    листенер вызывает сервисный метод который что то делает
//    Сервисный метод выполняет логику и отправляет событие в кафку

//    @KafkaHandler
//    public void carStatusHandler(CarDeleteStatus carDeleteStatus) {
//        if (carDeleteStatus.getCarDeleteStatus().equals(KafkaStatus.SUCCESS)) {
//            kafkaTemplate.send("houseEvents", deletePersonDto)
//        }
//    }

    // Получаем сообщение о том, что необходимо удалить паспорт
    @KafkaHandler
    public void carDeleteHandler(DeletePersonDto deletePersonDto) {
        carService.deletePersonCars(deletePersonDto.getPerson().getPersonId());
    }

    @KafkaHandler
    public void carRollbackHandler() {
        carService.rollbackDeletedPersonCars();
    }


}
