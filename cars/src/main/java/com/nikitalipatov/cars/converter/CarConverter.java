package com.nikitalipatov.cars.converter;

import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.common.dto.CarDto;
import com.nikitalipatov.common.dto.CarRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarConverter {

    public Car toEntity(Car car, CarRecord carRecord) {
        return car.toBuilder()
                .color(carRecord.color())
                .model(carRecord.model())
                .name(carRecord.name())
                .gosNumber(carRecord.gosNumber())
                .model(carRecord.model())
                .price(carRecord.price())
                .type(carRecord.type())
                .build();
    }

//    public Car toEntity(CarRecord carRecord, CarShop carShop) {
//        return Car.builder()
//                .color(carRecord.color())
//                .model(carRecord.model())
//                .name(carRecord.name())
//                .gosNumber(carRecord.gosNumber())
//                .model(carRecord.model())
//                .price(carRecord.price())
//                .type(carRecord.type())
//                .carShop(carShop)
//                .build();
//    }

    public Car toEntity(CarRecord carRecord) {
        return Car.builder()
                .color(carRecord.color())
                .model(carRecord.model())
                .name(carRecord.name())
                .gosNumber(carRecord.gosNumber())
                .model(carRecord.model())
                .price(carRecord.price())
                .type(carRecord.type())
                .build();
    }

    public List<Car> toEntity(List<CarRecord> carRecordList) {
        List<Car> cars = new ArrayList<>();
        for (CarRecord carRecord : carRecordList) {
            cars.add(toEntity(carRecord));
        }
        return cars;
    }

    public CarDto toDto(Car car) {
        return CarDto.builder()
                .gosNumber(car.getGosNumber())
                .name(car.getName())
                .color(car.getColor())
                .model(car.getModel())
                .type(car.getType())
                .price(car.getPrice())
                .build();
    }

    public List<CarDto> toDto(List<Car> carList) {
        var cars = new ArrayList<CarDto>();
        for (Car car : carList) {
            cars.add(toDto(car));
        }
        return cars;
    }


    public Car toEntityForShop(CarRecord carRecord) {
        return new Car(carRecord.gosNumber(), carRecord.model(), carRecord.name(), carRecord.type(), carRecord.color(), carRecord.price());
    }
}
