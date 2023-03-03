package com.nikitalipatov.passports.controller;

import com.nikitalipatov.common.dto.PassportDto;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/passport")
@RequiredArgsConstructor
public class PassportController {

    private final PassportService passportService;

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PassportDto create(@RequestBody int personId) {
        return passportService.create(personId);
    }

    @GetMapping(value = "/get/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public PassportDto getByOwnerId(@PathVariable int personId) {
        return passportService.getByOwnerId(personId);
    }

    @DeleteMapping(value = "/delete/{personId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int personId) {
        passportService.delete(personId);
    }
}
