package com.nikitalipatov.passports.service;

import com.nikitalipatov.common.dto.PassportDto;


public interface PassportService {

    PassportDto create(int personId);

    void delete(int personId);

    PassportDto getByOwnerId(int personId);
}
