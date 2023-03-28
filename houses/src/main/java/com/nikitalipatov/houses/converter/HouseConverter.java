package com.nikitalipatov.houses.converter;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.logs.MyLog;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class HouseConverter {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public MyLog toLog(String logType, int numOfEntities) {
        return MyLog.builder()
                .logType(logType)
                .logEntity("house")
                .numOfEntities(numOfEntities)
                .time(simpleDateFormat.format(new Date()))
                .build();
    }

    public MyLog toLog(int numOfEntities) {
        return MyLog.builder()
                .logType("update")
                .logEntity("house")
                .numOfEntities(numOfEntities)
                .time(simpleDateFormat.format(new Date()))
                .build();
    }

    public House toEntity(HouseDtoRequest houseDtoRequest) {
        return House.builder()
                .street(houseDtoRequest.getStreet())
                .number(houseDtoRequest.getNumber())
                .status(ModelStatus.ACTIVE.name())
                .build();
    }


    public House toEntity(House house, HouseDtoRequest houseDtoRequest) {
        return house.toBuilder()
                .street(houseDtoRequest.getStreet())
                .number(houseDtoRequest.getNumber())
                .status(ModelStatus.ACTIVE.name())
                .build();
    }

    public HouseDtoResponse toDto(House house) {
        return HouseDtoResponse.builder()
                .street(house.getStreet())
                .number(house.getNumber())
                .build();
    }

    public HousePersonDto toDto(HousePerson housePerson) {
        return HousePersonDto.builder()
                .houseId(housePerson.getHousePersonId().getHouseId())
                .personId(housePerson.getHousePersonId().getOwnerId())
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
