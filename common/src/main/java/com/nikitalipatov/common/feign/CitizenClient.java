package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.response.ActiveCitizen;
import com.nikitalipatov.common.dto.response.CitizenWithPassportDto;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "CitizenClient", url = "http://localhost:8082/api/person")
public interface CitizenClient {

    @GetMapping(value = "/rollback/{personId}")
    void rollbackCitizenCreation(@PathVariable int personId);

    @GetMapping(value = "/list")
    List<CitizenWithPassportDto> getAll();

    @GetMapping(value = "/number")
    int getNumOfCitizens();

    @GetMapping(value = "/active")
    List<ActiveCitizen> getActiveCitizens();
}
