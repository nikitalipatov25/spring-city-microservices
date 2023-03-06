package com.nikitalipatov.passports.service.impl;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.passports.converter.PassportConverter;
import com.nikitalipatov.passports.model.Passport;
import com.nikitalipatov.passports.repository.PassportRepository;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportConverter passportConverter;

    @Override
    public PassportDtoResponse create(int personId) {
        return passportConverter.toDto(passportRepository.save(passportConverter.toEntity(personId)));
    }

    public List<PassportDtoResponse> getAllByOwnerIds(List<Integer> ownerIds) {
        return passportConverter.toDto(passportRepository.findAllByOwnerIdIn(ownerIds));
    }

    public PassportDtoResponse getByOwnerId(int personId) {
        return passportConverter.toDto(getPassport(personId));
    }

    @Override
    public void delete(int personId) {
        passportRepository.deleteByOwnerId(personId);
    }

    public Passport getPassport(int personId) {
        return passportRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such passport with owner id " + personId)
        );
    }
}
