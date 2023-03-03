package com.nikitalipatov.houses.converter;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HouseConverter {

    public House toEntity(HouseDtoRequest houseDtoRequest) {
        return House.builder()
                .street(houseDtoRequest.street())
                .number(houseDtoRequest.number())
                .build();
    }

    public HousePerson toEntity(int houseId, int personId) {
        return HousePerson.builder()
                .houseId(houseId)
                .personId(personId)
                .build();
    }

    public House toEntityEdit(House house, HouseDtoRequest houseDtoRequest) {
        return house.toBuilder()
                .street(houseDtoRequest.street())
                .number(houseDtoRequest.number())
                .build();
    }

    public HouseDtoResponse toDto(House house) {
        return HouseDtoResponse.builder()
                .street(house.getStreet())
                .number(house.getNumber())
                .citizenIds(house.getHousePerson() == null ? null : house.getHousePerson()
                        .stream()
                        .map(HousePerson::getPersonId)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<HouseDtoResponse> toDto(List<House> houseList) {
        var houses = new ArrayList<HouseDtoResponse>();
        for (House house : houseList) {
            houses.add(toDto(house));
        }
        return houses;
    }
}
