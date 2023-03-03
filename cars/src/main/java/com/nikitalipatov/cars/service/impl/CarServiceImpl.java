package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;

    @Override
    public List<CarDtoResponse> getAll() {
        return carConverter.toDto(carRepository.findAll());
    }

    @Override
    @Transactional
    public CarDtoResponse create(int personId, CarDtoRequest carDtoRequest) {
        return carConverter.toDto(carRepository.save(carConverter.toEntity(carDtoRequest, personId)));
    }

    public List<CarDtoResponse> getCitizenCar(int personId) {
        return carConverter.toDto(carRepository.findAllByOwnerId(personId));
    }

    public void deletePersonCars(int personId) {
        carRepository.deleteAllByOwnerId(personId);
    }

    @Override
    @Transactional
    public void deleteCar(int carId) {
        carRepository.delete(getCar(carId));
    }

    @Override
    @Transactional
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
