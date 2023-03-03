package com.nikitalipatov.houses.controller;

import com.nikitalipatov.common.dto.HouseDto;
import com.nikitalipatov.common.dto.HouseRecord;
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
    public List<HouseDto> getAll() {
        return houseService.getAll();
    }

    @PutMapping(value = "{houseId}/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public HouseDto addCitizen(@PathVariable int houseId, @PathVariable int personId) {
        return houseService.addCitizen(houseId, personId);
    }

    @DeleteMapping(value = "/delete/person/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void removePerson(@PathVariable int personId) {
        houseService.removePerson(personId);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public HouseDto create(@RequestBody HouseRecord houseRecord) {
        return houseService.create(houseRecord);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        houseService.delete(id);
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public HouseDto edit(@PathVariable int id, @RequestBody HouseRecord houseRecord) {
        return houseService.edit(id, houseRecord);
    }
}
