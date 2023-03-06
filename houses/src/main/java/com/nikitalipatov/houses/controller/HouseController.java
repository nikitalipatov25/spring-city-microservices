package com.nikitalipatov.houses.controller;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/house")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping(value = "/list")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<HouseDtoResponse> getAll() {
        return houseService.getAll();
    }

    @PutMapping(value = "{houseId}/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public HousePersonDto addCitizen(@PathVariable int houseId, @PathVariable int personId) {
        return houseService.addCitizen(houseId, personId);
    }

    @DeleteMapping(value = "/delete/{houseId}/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void removePerson(@PathVariable int houseId, @PathVariable int personId) {
        houseService.removePerson(houseId, personId);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public HouseDtoResponse create(@RequestBody HouseDtoRequest houseDtoRequest) {
        return houseService.create(houseDtoRequest);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        houseService.delete(id);
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public HouseDtoResponse edit(@PathVariable int id, @RequestBody HouseDtoRequest houseDtoRequest) {
        return houseService.edit(id, houseDtoRequest);
    }
}
