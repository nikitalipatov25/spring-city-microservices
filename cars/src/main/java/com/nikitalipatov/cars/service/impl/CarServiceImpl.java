package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.ActiveCitizen;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.enums.*;
import com.nikitalipatov.common.error.LotteryException;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.CitizenClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;
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

    @Lazy @Autowired private CarServiceImpl carService;

    @PostConstruct
    public void init() {
        numberOfCars.set(carRepository.countActiveCars());
    }

    @Scheduled(fixedDelay = 20000)
    public void updateNumOfCars() {
        stompSession.send("/app/logs", carConverter.toLog(LogType.UPDATE.name(), numberOfCars.get()));
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 10000)
    public void startLottery() {
        isLottery.set(true);
        ActiveCitizen randomCitizen = carService.chooseWinner();
        Car lotteryCar = Car.builder().ownerId(randomCitizen.getId()).build();
        try {
            carRepository.save(lotteryCar);
            stompSession.send("/app/logs", carConverter.toLog(LogType.CREATE.name(), numberOfCars.get() + 1));
            numberOfCars.getAndIncrement();
            isLottery.set(false);
        } catch (Exception e) {
            deleteLotteryCar(lotteryCar.getId());
            throw new LotteryException(LotteryStatus.CANT_RUN_LOTTERY.name());
        } finally {
            isLottery.set(false);
        }
    }

    public ActiveCitizen chooseWinner() {
        try {
            List<ActiveCitizen> citizenList = citizenClient.getActiveCitizens();
            return citizenList.get(new Random().nextInt(citizenList.size()));
        } catch (Exception e) {
            isLottery.set(false);
            throw new LotteryException(LotteryStatus.CANT_CHOOSE_WINNER.name());
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
            throw new LotteryException(LotteryStatus.LOTTERY_IS_GOING.name());
        }
        var car = carRepository.save(carConverter.toEntity(carDtoRequest));
        stompSession.send("/app/logs", carConverter.toLog(LogType.CREATE.name(), numberOfCars.get() + 1));
        numberOfCars.getAndIncrement();
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

    private void deleteLotteryCar(int carId) {
        var car = getCar(carId);
        carRepository.delete(car);
    }
}
