package com.nikitalipatov.cars.service;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.dto.CarDto;
import com.nikitalipatov.common.dto.CarRecord;

import java.util.List;

public interface CarService {

    List<CarDto> getAll();

    CarDto create(int personId, CarRecord carRecord);

    List<Integer> create(List<CarRecord> carRecord);

    void deleteCar(int carId);

    CarDto editCar(int carId, CarRecord carRecord);

    Car getCar(int carId);
}