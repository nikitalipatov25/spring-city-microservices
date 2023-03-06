package com.nikitalipatov.common.feign;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "PassportClient", url = "http://localhost:8080/api/passport")
public interface PassportClient {

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    List<PassportDtoResponse> getPassportsByOwnerIds(@RequestBody List<Integer> ownersId);

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    PassportDtoResponse create(@RequestBody int personId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{personId}")
    void delete(@PathVariable int personId);
}
