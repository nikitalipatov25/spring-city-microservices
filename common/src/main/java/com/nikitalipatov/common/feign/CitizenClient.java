package com.nikitalipatov.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "CitizenClient", url = "http://localhost:8082/api/person")
public interface CitizenClient {

    @RequestMapping(method = RequestMethod.GET, value = "/rollback/{personId}")
    void rollbackCitizenCreation(@PathVariable int personId);
}
