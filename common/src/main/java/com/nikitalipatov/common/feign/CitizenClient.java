package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "CitizenClient", url = "http://localhost:8082/api/person")
public interface CitizenClient {

    @RequestMapping(method = RequestMethod.GET, value = "/rollback/{personId}")
    void rollbackCitizenCreation(@PathVariable int personId);

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    List<PersonDtoResponse> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/number")
    int getNumOfCitizens();
}
