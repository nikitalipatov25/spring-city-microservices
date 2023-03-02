package com.nikitalipatov.houses.converter;

import com.nikitalipatov.common.dto.HouseDto;
import com.nikitalipatov.common.dto.HouseRecord;
import com.nikitalipatov.houses.model.House;

import java.util.ArrayList;
import java.util.List;

public class HouseConverter {

    public House toEntity(House house, HouseRecord houseRecord) {
        return house.toBuilder()
                .city(houseRecord.city())
                .street(houseRecord.street())
                .number(houseRecord.number())
                .build();
    }

    public HouseDto toDto(House house) {
        return HouseDto.builder()
                .city(house.getCity())
                .street(house.getStreet())
                .number(house.getNumber())
                .build();
    }

    public List<HouseDto> toDto(List<House> houseList) {
        var houses = new ArrayList<HouseDto>();
        for (House house : houseList) {
            houses.add(toDto(house));
        }
        return houses;
    }
}
