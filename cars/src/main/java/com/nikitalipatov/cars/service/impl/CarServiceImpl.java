package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.common.dto.kafka.CitizenEvent;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    public List<CarDtoResponse> getAll() {
        return carConverter.toDto(carRepository.findAll());
    }

    @Override
    public CarDtoResponse create(int personId, CarDtoRequest carDtoRequest) {
        return carConverter.toDto(carRepository.save(carConverter.toEntity(carDtoRequest, personId)));
    }

    public List<CarDtoResponse> getCitizenCar(int personId) {
        return carConverter.toDto(carRepository.findAllByOwnerId(personId));
    }

    @Override
    @Transactional
    public void rollbackDeletedPersonCars(int personId) {
        var personCars = carRepository.findAllByOwnerId(personId);
        personCars.forEach(car -> car.setStatus(ModelStatus.ACTIVE));
        carRepository.saveAll(personCars);
    }

    @Transactional
    public void deletePersonCars(int personId) {
        try {
            var personCars = carRepository.findAllByOwnerId(personId);
            personCars.forEach(car -> car.setStatus(ModelStatus.DELETED));
            carRepository.saveAll(personCars);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.CAR_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
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
