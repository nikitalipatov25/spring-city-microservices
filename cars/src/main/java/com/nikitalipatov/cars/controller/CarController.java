package com.nikitalipatov.cars.controller;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.feign.CarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/car")
public class CarController implements CarClient {

    private final CarService carService;

    @Override
    public CarDtoResponse createCars(@PathVariable int userId, @RequestBody CarDtoRequest carDtoRequest) {
        return carService.create(userId, carDtoRequest);
    }

    @Override
    public List<CarDtoResponse> getCitizenCar(@PathVariable int personId) {
        return carService.getCitizenCar(personId);
    }

    @Override
    public void deletePersonCars(@PathVariable int personId) {
        carService.deletePersonCars(personId);
    }

    @Override
    public List<CarDtoResponse> getAll() {
        return carService.getAll();
    }

    @Override
    public void delete(@PathVariable int id) {
        carService.deleteCar(id);
    }

    @Override
    public CarDtoResponse edit(@PathVariable int id, @RequestBody CarDtoRequest carDtoRequest) {
        return carService.editCar(id, carDtoRequest);
    }
}