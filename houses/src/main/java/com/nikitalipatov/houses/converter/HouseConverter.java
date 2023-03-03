package com.nikitalipatov.houses.converter;

import com.nikitalipatov.common.dto.HouseDto;
import com.nikitalipatov.common.dto.HouseRecord;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class HouseConverter {

    public House toEntity(HouseRecord houseRecord) {
        return House.builder()
                .street(houseRecord.street())
                .number(houseRecord.number())
                .build();
    }

    public HousePerson toEntity(int houseId, int personId) {
        return HousePerson.builder()
                .houseId(houseId)
                .personId(personId)
                .build();
    }

    public House toEntityEdit(House house, HouseRecord houseRecord) {
        return house.toBuilder()
                .street(houseRecord.street())
                .number(houseRecord.number())
                .build();
    }

    public HouseDto toDto(House house) {
        return HouseDto.builder()
                .street(house.getStreet())
                .number(house.getNumber())
                .citizenIds(house.getHousePerson() == null ? null : house.getHousePerson()
                        .stream()
                        .map(HousePerson::getPersonId)
                        .collect(Collectors.toList()))
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
