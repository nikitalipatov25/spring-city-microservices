package com.nikitalipatov.cars.converter;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarConverter {

    public Car toEntity(Car car, CarDtoRequest carDtoRequest) {
        return car.toBuilder()
                .color(carDtoRequest.getColor())
                .model(carDtoRequest.getModel())
                .name(carDtoRequest.getName())
                .gosNumber(carDtoRequest.getGosNumber())
                .status(carDtoRequest.getStatus())
                .build();
    }


    public Car toEntity(CarDtoRequest carDtoRequest, int personId) {
        return Car.builder()
                .color(carDtoRequest.getColor())
                .model(carDtoRequest.getModel())
                .name(carDtoRequest.getName())
                .gosNumber(carDtoRequest.getGosNumber())
                .ownerId(personId)
                .status(carDtoRequest.getStatus())
                .build();
    }

    public CarDtoResponse toDto(Car car) {
        return CarDtoResponse.builder()
                .gosNumber(car.getGosNumber())
                .name(car.getName())
                .color(car.getColor())
                .model(car.getModel())
                .price(car.getPrice())
                .status(car.getStatus())
                .build();
    }

    public List<CarDtoResponse> toDto(List<Car> carList) {
        var cars = new ArrayList<CarDtoResponse>();
        for (Car car : carList) {
            cars.add(toDto(car));
        }
        return cars;
    }

}
