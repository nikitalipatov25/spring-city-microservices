package com.nikitalipatov.passports.service;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;

import java.util.List;


public interface PassportService {

    PassportDtoResponse create(int personId);

    void delete(int personId);

    PassportDtoResponse getByOwnerId(int personId);

    List<PassportDtoResponse> getAllByOwnerIds(List<Integer> ownerIds);
}
