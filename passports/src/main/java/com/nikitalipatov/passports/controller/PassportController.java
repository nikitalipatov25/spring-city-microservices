package com.nikitalipatov.passports.controller;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/passport")
@RequiredArgsConstructor
public class PassportController {

    private final PassportService passportService;

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PassportDtoResponse create(@RequestBody int personId) {
        return passportService.create(personId);
    }

    @GetMapping(value = "/get")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<PassportDtoResponse> getPassportsByOwnerIds(@RequestBody List<Integer> ownersId) {
        return passportService.getAllByOwnerIds(ownersId);
    }

    @GetMapping(value = "/get/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PassportDtoResponse getByOwnerId(@PathVariable int personId) {
        return passportService.getByOwnerId(personId);
    }

    @DeleteMapping(value = "/delete/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int personId) {
        passportService.delete(personId);
    }
}
