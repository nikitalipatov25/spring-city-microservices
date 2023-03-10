package com.nikitalipatov.passports.service;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;


public interface PassportService {

    PassportDtoResponse create(int personId);

    void delete(int personId);

    void rollback(PassportDtoResponse passportDtoResponse);

    PassportDtoResponse getByOwnerId(int personId);

    List<PassportDtoResponse> getAllByOwnerIds(List<Integer> ownerIds);
}
