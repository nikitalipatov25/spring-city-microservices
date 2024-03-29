package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.request.CarDtoRequest;
import com.nikitalipatov.common.dto.response.CarDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "CarClient", url = "http://localhost:8080/api/car")
public interface CarClient {

//    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/person/{personId}")
    @DeleteMapping(value = "/delete/person/{personId}")
    void deletePersonCars(@PathVariable int personId);

//    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @PostMapping(value = "/create")
    CarDtoResponse createCars(@RequestBody CarDtoRequest carDtoRequest);

//    @RequestMapping(method = RequestMethod.GET, value = "/list/{personId}")
    @GetMapping(value = "/list/{personId}")
    List<CarDtoResponse> getCitizenCar(@PathVariable int personId);

//    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @GetMapping(value = "/list")
    List<CarDtoResponse> getAll();

//    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    @DeleteMapping(value = "/delete/{id}")
    void delete(@PathVariable int id);

//    @RequestMapping(method = RequestMethod.PUT, value = "/edit/{id}")
    @PutMapping(value = "/edit/{id}")
    CarDtoResponse edit(@PathVariable int id, @RequestBody CarDtoRequest carDtoRequest);
}
