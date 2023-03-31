package com.nikitalipatov.cars.mapper;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car carDtoRequestToCar(CarDtoRequest carDtoRequest);
    CarDtoResponse carToCarDtoResponse(Car car);
    Car updateModel(CarDtoRequest carDtoRequest, @MappingTarget Car car);
}
