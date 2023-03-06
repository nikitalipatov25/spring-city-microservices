package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "HouseClient", url = "http://localhost:8080/api/house")
public interface HouseClient {

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{personId}")
    void removePerson(@PathVariable int personId);

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    List<HouseDtoResponse> getAll();

    @RequestMapping(method = RequestMethod.PUT, value = "/{houseId}/{personId}")
    HousePersonDto addCitizen(@PathVariable int houseId, @PathVariable int personId);

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    HouseDtoResponse create(@RequestBody HouseDtoRequest houseDtoRequest);

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    void delete(@PathVariable int id);

    @RequestMapping(method = RequestMethod.PUT, value = "/edit/{id}")
    HouseDtoResponse edit(@PathVariable int id, @RequestBody HouseDtoRequest houseDtoRequest);
}
