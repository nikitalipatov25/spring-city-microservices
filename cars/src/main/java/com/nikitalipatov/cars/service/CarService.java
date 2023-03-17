package com.nikitalipatov.cars.service;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;

import java.util.List;

public interface CarService {

    List<CarDtoResponse> getAll();

    CarDtoResponse create(int personId, CarDtoRequest carDtoRequest);

    void deleteCar(int carId);

    CarDtoResponse editCar(int carId, CarDtoRequest carDtoRequest);

    Car getCar(int carId);

    void rollback(int personId, List<CarDtoResponse> carList);

    List<CarDtoResponse> getCitizenCar(int personId);

    void deletePersonCars(int personId);
}