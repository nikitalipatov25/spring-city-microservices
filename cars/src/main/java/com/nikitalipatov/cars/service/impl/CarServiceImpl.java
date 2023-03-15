package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.common.dto.kafka.CarDeleteStatus;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
    public void rollbackDeletedPersonCars(int personId, List<CarDtoResponse> carList) {
        carList.forEach(car -> carRepository.save(
                Car.builder()
                        .ownerId(personId)
                        .color(car.getColor())
                        .gosNumber(car.getGosNumber())
                        .model(car.getModel())
                        .name(car.getName())
                        .price(car.getPrice())
                        .build()
        ));
    }

    public void deletePersonCars(int personId) {
        var personCars = getCitizenCar(personId);
        try {
            carRepository.deleteAllByOwnerId(personId);
            kafkaTemplate.send("carEvents", CarDeleteStatus.builder()
                    .carList(personCars)
                    .carDeleteStatus(KafkaStatus.SUCCESS));
        } catch (Exception e) {
            kafkaTemplate.send("carEvents", CarDeleteStatus.builder()
                    .carList(personCars)
                    .carDeleteStatus(KafkaStatus.FAIL));
        }
        carRepository.deleteAllByOwnerId(personId);

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
