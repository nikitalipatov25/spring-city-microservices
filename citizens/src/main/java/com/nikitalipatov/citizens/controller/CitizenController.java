package com.nikitalipatov.citizens.controller;

import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.ActiveCitizen;
import com.nikitalipatov.common.dto.response.CitizenWithPassportDto;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.feign.CitizenClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/person")
public class CitizenController implements CitizenClient {

    private final CitizenService personService;

    @Override
    public void rollbackCitizenCreation(int personId) {
        personService.rollbackCitizenCreation(personId);
    }

    @Override
    public int getNumOfCitizens() {
        return personService.getNumOfActiveCitizens();
    }

//    @PreAuthorize("hasRole('write')")
    @GetMapping(value = "/list")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<CitizenWithPassportDto> getAll() {
        return personService.getAll();
    }

//    @PreAuthorize("hasRole('read')")
    @GetMapping(value = "/{name}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PersonDtoResponse getByName(@PathVariable String name) {
        return personService.getByName(name);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PersonDtoResponse create(@RequestBody PersonDtoRequest personDtoRequest) {
        return personService.create(personDtoRequest);
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PersonDtoResponse edit(@PathVariable int id, @RequestBody PersonDtoRequest personDtoRequest) {
        return personService.edit(id, personDtoRequest);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        personService.delete(id);
    }

    @GetMapping(value = "/active")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<ActiveCitizen> getActiveCitizens() {
        return personService.getActiveCitizens();
    }
}
