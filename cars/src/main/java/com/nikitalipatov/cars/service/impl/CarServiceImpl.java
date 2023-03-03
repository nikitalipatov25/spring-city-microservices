package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.common.dto.CarDto;
import com.nikitalipatov.common.dto.CarRecord;
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
    public List<CarDto> getAll() {
        return carConverter.toDto(carRepository.findAll());
    }

    @Override
    @Transactional
    public CarDto create(int personId, CarRecord carRecord) {
        return carConverter.toDto(carRepository.save(carConverter.toEntity(carRecord, personId)));
    }

    public List<CarDto> getCitizenCar(int personId) {
        return carConverter.toDto(carRepository.findAllByOwnerId(personId));
    }

    public void deletePersonCars(int personId) {
        carRepository.deleteAllByOwnerId(personId);
    }

//    @Override
//    @Transactional
//    public List<Integer> create(List<CarRecord> carRecords) {
//        var cars = carConverter.toEntity(carRecords);
//        carRepository.saveAll(cars);
//        return cars.stream()
//                .map((Car::getId))
//                .toList();
//    }

    @Override
    @Transactional
    public void deleteCar(int carId) {
        carRepository.delete(getCar(carId));
    }

    @Override
    @Transactional
    public CarDto editCar(int carId, CarRecord carRecord) {
        Car car = getCar(carId);
        return carConverter.toDto(carRepository.save(carConverter.toEntity(car, carRecord)));
    }

    @Override
    public Car getCar(int carId) {
        return carRepository.findById(carId).orElseThrow(
                () -> new ResourceNotFoundException("No such car with id " + carId)
        );
    }
}
