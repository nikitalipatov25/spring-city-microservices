package com.nikitalipatov.cars.controller;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/car")
public class CarController {

    private final CarService carService;

    @PostMapping(value = "/create/{userId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CarDtoResponse createCars(@PathVariable int userId, @RequestBody CarDtoRequest carDtoRequest) {
        return carService.create(userId, carDtoRequest);
    }

    @GetMapping(value = "/list/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<CarDtoResponse> getCitizenCar(@PathVariable int personId) {
        return carService.getCitizenCar(personId);
    }

    @DeleteMapping(value = "/delete/person/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deletePersonCars(@PathVariable int personId) {
        carService.deletePersonCars(personId);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<CarDtoResponse> getAll() {
        return carService.getAll();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        carService.deleteCar(id);
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CarDtoResponse edit(@PathVariable int id, @RequestBody CarDtoRequest carDtoRequest) {
        return carService.editCar(id, carDtoRequest);
    }
}