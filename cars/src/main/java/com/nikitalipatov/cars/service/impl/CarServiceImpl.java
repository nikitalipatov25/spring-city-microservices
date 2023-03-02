package com.nikitalipatov.cars.service.impl;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.cars.repository.CarRepository;
import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.citizens.service.CitizenService;
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
    private final CitizenService personService;

    @Override
    public List<CarDto> getAll() {
        return carConverter.toDto(carRepository.findAll());
    }

    @Override
    @Transactional
    public CarDto create(int personId, CarRecord carRecord) {
        Car car = new Car(carRecord.gosNumber(), carRecord.model(), carRecord.type(), carRecord.color(), carRecord.name());
        car.setPerson(personService.getPerson(personId));
        System.out.println(car);
        return carConverter.toDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public List<Integer> create(List<CarRecord> carRecords) {
        var cars = carConverter.toEntity(carRecords);
        carRepository.saveAll(cars);
        return cars.stream()
                .map((Car::getId))
                .toList();
    }

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
