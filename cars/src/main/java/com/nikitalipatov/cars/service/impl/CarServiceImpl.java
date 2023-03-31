package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.LogType;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.error.LotteryException;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.CitizenClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import java.util.concurrent.atomic.AtomicInteger;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private static final AtomicBoolean isLottery = new AtomicBoolean(false);
    private static final AtomicInteger numberOfCars = new AtomicInteger(0);
    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final StompSession stompSession;
    private final CitizenClient citizenClient;

    @PostConstruct
    public void init() {
        numberOfCars.set(carRepository.countActiveCars());
    }

    @Scheduled(fixedDelay = 20000)
    public void updateNumOfCars() {
        stompSession.send("/app/logs", carConverter.toLog(LogType.UPDATE.name(), numberOfCars.get()));
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void lottery() {
        try {
            int numOfCitizens = citizenClient.getNumOfCitizens();
            isLottery.set(true);
            int randomCitizen = ThreadLocalRandom.current().nextInt(1, numOfCitizens + 1);
            var lotteryCar = CarDtoRequest.builder().ownerId(randomCitizen).build();
            carRepository.save(carConverter.toEntity(lotteryCar));
            numberOfCars.getAndIncrement();
            stompSession.send("/app/logs", carConverter.toLog(LogType.CREATE.name(), numberOfCars.get()));
            Thread.sleep(30000);
            isLottery.set(false);
        } catch (Exception e) {
            throw new LotteryException("Лотерея не может быть проведена");
        } finally {
            isLottery.set(false);
        }
    }

    @Override
    public List<CarDtoResponse> getAll() {
        return carConverter.toDto(carRepository.findAllActive());
    }

    @Override
    @SneakyThrows
    @Transactional
    public CarDtoResponse create(CarDtoRequest carDtoRequest) {
        if (isLottery.get()) {
            throw new LotteryException("Идет лотерея. Все продавцы заняты!");
        }
        var car = carRepository.save(carConverter.toEntity(carDtoRequest));
        numberOfCars.getAndIncrement();
        stompSession.send("/app/logs", carConverter.toLog(LogType.CREATE.name(), numberOfCars.get()));
        return carConverter.toDto(car);
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
        numberOfCars.getAndAdd(personCars.size());
        stompSession.send("/app/logs", carConverter.toLog(LogType.CREATE.name(), numberOfCars.get()));
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
            numberOfCars.getAndAdd( - personCars.size());
            stompSession.send("/app/logs", carConverter.toLog(LogType.DELETE.name(), numberOfCars.get()));
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
        numberOfCars.getAndDecrement();
        stompSession.send("/app/logs", carConverter.toLog(LogType.DELETE.name(), numberOfCars.get()));
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
