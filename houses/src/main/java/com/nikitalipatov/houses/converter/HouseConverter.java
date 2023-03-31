package com.nikitalipatov.houses.converter;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.common.enums.LogEntity;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.mapper.LogMapper;
import com.nikitalipatov.houses.mapper.HouseMapper;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Component
@RequiredArgsConstructor
public class HouseConverter {

    private final HouseMapper houseMapper;

    public LogDto toLog(String logType, int numOfEntities) {
        return LogMapper.INSTANCE.toLogDto(logType, LogEntity.HOUSE.name(), new Date(), numOfEntities);
    }

    public House toEntity(HouseDtoRequest houseDtoRequest) {
        return houseMapper.toHouse(houseDtoRequest);
    }

    public House toEntity(House house, HouseDtoRequest houseDtoRequest) {
        return houseMapper.updateModel(houseDtoRequest, house);
    }

    public HouseDtoResponse toDto(House house) {
        return houseMapper.toHouseDtoResponse(house);
    }

    public HousePersonDto toDto(HousePerson housePerson) {
        return houseMapper.toHousePersonDto(housePerson);
    }

    public List<HouseDtoResponse> toDto(List<House> houseList) {
        var houses = new ArrayList<HouseDtoResponse>();
        for (House house : houseList) {
            houses.add(toDto(house));
        }
        return houses;
    }
}
