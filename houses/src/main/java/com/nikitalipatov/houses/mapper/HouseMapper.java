package com.nikitalipatov.houses.mapper;

import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HouseMapper {
    House toHouse(HouseDtoRequest houseDtoRequest);
    HouseDtoResponse toHouseDtoResponse(House house);
    HousePersonDto toHousePersonDto(HousePerson housePerson);
    House updateModel(HouseDtoRequest houseDtoRequest, @MappingTarget House house);
}
