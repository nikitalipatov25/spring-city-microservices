package com.nikitalipatov.cars.controller;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.common.dto.CarDto;
import com.nikitalipatov.common.dto.CarRecord;
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
    public CarDto createCars(@PathVariable int userId, @RequestBody CarRecord carRecord) {
        return carService.create(userId, carRecord);
    }

    @GetMapping(value = "/list/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<CarDto> getCitizenCar(@PathVariable int personId) {
        return carService.getCitizenCar(personId);
    }

    @DeleteMapping(value = "/delete/person/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deletePersonCars(@PathVariable int personId) {
        carService.deletePersonCars(personId);
    }

//    @PostMapping(value = "/create")
//    @ResponseStatus(value = HttpStatus.ACCEPTED)
//    public List<Integer> createCars(@RequestBody List<CarRecord> carRecord) {
//        return carService.create(carRecord);
//    }

    @GetMapping(value = "/list")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<CarDto> getAll() {
        return carService.getAll();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        carService.deleteCar(id);
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CarDto edit(@PathVariable int id, @RequestBody CarRecord carRecord) {
        return carService.editCar(id, carRecord);
    }
}