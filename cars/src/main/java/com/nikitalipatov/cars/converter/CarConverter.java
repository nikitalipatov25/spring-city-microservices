package com.nikitalipatov.cars.converter;

import com.nikitalipatov.cars.mapper.CarMapper;
import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.enums.LogEntity;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Component
@RequiredArgsConstructor
public class CarConverter {

    private final CarMapper carMapper;

    public LogDto toLog(String logType, int numOfEntities) {
        return LogMapper.INSTANCE.toLogDto(logType, LogEntity.CAR.name(), new Date(), numOfEntities);
    }

    public Car toEntity(Car car, CarDtoRequest carDtoRequest) {
        return carMapper.updateModel(carDtoRequest, car);
    }

    public Car toEntity(CarDtoRequest carDtoRequest) {
        return carMapper.carDtoRequestToCar(carDtoRequest);
    }

    public CarDtoResponse toDto(Car car) {
        return carMapper.carToCarDtoResponse(car);
    }

    public List<CarDtoResponse> toDto(List<Car> carList) {
        var cars = new ArrayList<CarDtoResponse>();
        for (Car car : carList) {
            cars.add(toDto(car));
        }
        return cars;
    }

}
