package com.nikitalipatov.houses.service;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.houses.model.House;

import java.util.List;

public interface HouseService {

    void rollback(int personId, List<HouseDtoResponse> houseDtoResponseList);

    List<HouseDtoResponse> getAll();

    HouseDtoResponse create(HouseDtoRequest houseDtoRequest);

    void delete(int houseId);

    HouseDtoResponse edit(int houseId, HouseDtoRequest houseDtoRequest);

    House getHouse(int houseId);

    List<HouseDtoResponse> getPersonHouses(int ownerId);

    void removePerson(int personId);

    HousePersonDto addCitizen(int houseId, int personId);
}
