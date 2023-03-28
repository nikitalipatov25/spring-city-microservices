package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.CitizenClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final StompSession stompSession;
    private final CitizenClient citizenClient;
    private static AtomicBoolean isLottery = new AtomicBoolean(false);
    private static int numberOfCars;


    @PostConstruct
    public void init() {
        numberOfCars = carRepository.countActiveCars();
    }

    @Scheduled(fixedDelay = 300000)
    public void updateNumOfCars() {
        stompSession.send("/app/logs", carConverter.toLog(numberOfCars));
    }

    @Scheduled(fixedDelay = 1000)
    public void lottery() throws InterruptedException {
        isLottery.set(true);
        int numOfCitizens = citizenClient.getNumOfCitizens();
        var lotteryCar = CarDtoRequest.builder()
                .ownerId(ThreadLocalRandom.current().nextInt(1, numOfCitizens + 1))
                .color("Red")
                .model("BMW")
                .name("M5")
                .gosNumber("LOTTERY")
                .status(ModelStatus.ACTIVE)
                .build();
        carRepository.save(carConverter.toEntity(lotteryCar));
        stompSession.send("/app/logs", carConverter.toLog("create", 1));
        numberOfCars = numberOfCars + 1;
        Thread.sleep(60000);
        isLottery.set(false);
    }

    @Override
    public List<CarDtoResponse> getAll() {
        return carConverter.toDto(carRepository.findAllActive());
    }

    @Override
    public CarDtoResponse create(CarDtoRequest carDtoRequest) throws InterruptedException {
        if (isLottery.get()) {
            throw new RuntimeException("Идет лотерея. Все продавцы заняты!");
        } else {
            var car = carRepository.save(carConverter.toEntity(carDtoRequest));
            stompSession.send("/app/logs", carConverter.toLog("create", 1));
            numberOfCars = numberOfCars + 1;
            return carConverter.toDto(car);
        }
    }

    public List<CarDtoResponse> getCitizenCar(int personId) {
        return carConverter.toDto(carRepository.findAllByOwnerId(personId));
    }

    @Override
    @Transactional
    public void rollbackDeletedPersonCars(int personId) {
        var personCars = carRepository.findAllByOwnerId(personId);
        personCars.forEach(car -> car.setStatus(ModelStatus.ACTIVE.name()));
        carRepository.saveAll(personCars);
        stompSession.send("/app/logs", carConverter.toLog("create", personCars.size()));
        numberOfCars = numberOfCars + personCars.size();
    }

    @Transactional
    public void deletePersonCars(int personId) {
        try {
            var personCars = carRepository.findAllByOwnerId(personId);
            personCars.forEach(car -> car.setStatus(ModelStatus.DELETED.name()));
            carRepository.saveAll(personCars);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.CAR_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
            stompSession.send("/app/logs", carConverter.toLog("delete", personCars.size()));
            numberOfCars = numberOfCars - personCars.size();
        } catch (Exception e) {
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.FAIL,
                    EventType.CAR_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        }
    }

    @Override
    public void deleteCar(int carId) {
        carRepository.delete(getCar(carId));
        stompSession.send("/app/logs", carConverter.toLog("delete", 1));
        numberOfCars = numberOfCars - 1;
    }

    @Override
    public CarDtoResponse editCar(int carId, CarDtoRequest carDtoRequest) {
        Car car = getCar(carId);
        return carConverter.toDto(carRepository.save(carConverter.toEntity(car, carDtoRequest)));
    }

    @Override
    public Car getCar(int carId) {
        return carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("No such car with id " + carId)
        );
    }
}
