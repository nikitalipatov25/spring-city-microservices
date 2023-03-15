package com.nikitalipatov.houses.controller;

import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/house")
@RequiredArgsConstructor
public class HouseController implements HouseClient {

    private final HouseService houseService;

    public void rollbackDeletedCitizenFromHouses(int personId, List<HouseDtoResponse> houseDtoResponseList) {
        houseService.rollbackDeletedCitizenFromHouses(personId, houseDtoResponseList);
    }

    @Override
    public List<HouseDtoResponse> getAll() {
        return houseService.getAll();
    }

    @Override
    public HousePersonDto addCitizen(@PathVariable int houseId, @PathVariable int personId) {
        return houseService.addCitizen(houseId, personId);
    }

    @Override
    public void removePerson(@PathVariable int personId) {
        houseService.removePerson(personId);
    }

    @Override
    public HouseDtoResponse create(@RequestBody HouseDtoRequest houseDtoRequest) {
        return houseService.create(houseDtoRequest);
    }

    @Override
    public void delete(@PathVariable int id) {
        houseService.delete(id);
    }

    @Override
    public HouseDtoResponse edit(@PathVariable int id, @RequestBody HouseDtoRequest houseDtoRequest) {
        return houseService.edit(id, houseDtoRequest);
    }
}
