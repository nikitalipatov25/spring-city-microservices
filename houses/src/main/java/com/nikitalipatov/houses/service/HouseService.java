package com.nikitalipatov.houses.service;

import com.nikitalipatov.common.dto.HouseDto;
import com.nikitalipatov.common.dto.HouseRecord;
import com.nikitalipatov.houses.model.House;

import java.util.List;

public interface HouseService {

    List<HouseDto> getAll();

    HouseDto create(HouseRecord houseRecord);

    void delete(int houseId);

    HouseDto edit(int houseId, HouseRecord houseRecord);

    House getHouse(int houseId);

    void removePerson(int personId);

    HouseDto addCitizen(int houseId, int personId);
}
