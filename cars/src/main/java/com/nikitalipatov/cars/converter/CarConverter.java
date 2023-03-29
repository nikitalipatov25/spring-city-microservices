package com.nikitalipatov.cars.converter;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.enums.ModelStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Component
public class CarConverter {

    public LogDto toLog(String logType, int numOfEntities) {
        return LogDto.builder()
                .logType(logType)
                .logEntity("car")
                .numOfEntities(numOfEntities)
                .time(SIMPLE_DATE_FORMAT.format(new Date()))
                .build();
    }

    public LogDto toLog(int numOfEntities) {
        return LogDto.builder()
                .logType("update")
                .logEntity("car")
                .numOfEntities(numOfEntities)
                .time(SIMPLE_DATE_FORMAT.format(new Date()))
                .build();
    }

    public Car toEntity(Car car, CarDtoRequest carDtoRequest) {
        return car.toBuilder()
                .color(carDtoRequest.getColor())
                .model(carDtoRequest.getModel())
                .name(carDtoRequest.getName())
                .gosNumber(carDtoRequest.getGosNumber())
                .status(ModelStatus.ACTIVE.name())
                .build();
    }


    public Car toEntity(CarDtoRequest carDtoRequest) {
        return Car.builder()
                .color(carDtoRequest.getColor())
                .model(carDtoRequest.getModel())
                .name(carDtoRequest.getName())
                .gosNumber(carDtoRequest.getGosNumber())
                .ownerId(carDtoRequest.getOwnerId())
                .status(ModelStatus.ACTIVE.name())
                .build();
    }

    public CarDtoResponse toDto(Car car) {
        return CarDtoResponse.builder()
                .gosNumber(car.getGosNumber())
                .name(car.getName())
                .color(car.getColor())
                .model(car.getModel())
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
