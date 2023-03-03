package com.nikitalipatov.passports.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient
public interface PassportClient {
}
