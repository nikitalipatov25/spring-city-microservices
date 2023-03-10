package com.nikitalipatov.passports.service.impl;

import com.nikitalipatov.common.dto.response.PersonCreationDto;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.passports.converter.PassportConverter;
import com.nikitalipatov.passports.model.Passport;
import com.nikitalipatov.passports.repository.PassportRepository;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportConverter passportConverter;
    private final KafkaTemplate<String, PersonCreationDto> kafkaTemplate;

    @Override
    public void rollback(PassportDtoResponse passportDtoResponse) {
        passportRepository.save(new Passport(
                passportDtoResponse.getOwnerId(),
                passportDtoResponse.getSerial(),
                passportDtoResponse.getNumber()
        ));
    }

    @Override
    public PassportDtoResponse create(int personId) {
        Passport passport = new Passport();
        try {
            passport = passportRepository.save(passportConverter.toEntity(personId));
            kafkaTemplate.send("passportEvents", new PersonCreationDto("ok", passport.getOwnerId()));
        } catch (Exception e) {
            kafkaTemplate.send("passportEvents", new PersonCreationDto("not ok", personId));
        }
        return passportConverter.toDto(passport);
    }

    public List<PassportDtoResponse> getAllByOwnerIds(List<Integer> ownerIds) {
        return passportConverter.toDto(passportRepository.findAllByOwnerIdIn(ownerIds));
    }

    public PassportDtoResponse getByOwnerId(int personId) {
        return passportConverter.toDto(getPassport(personId));
    }

    @Override
    public void delete(int personId) {
        try {
            passportRepository.deleteByOwnerId(personId);
            kafkaTemplate.send()
        } catch (Exception e) {

        }
    }

    public Passport getPassport(int personId) {
        return passportRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such passport with owner id " + personId)
        );
    }
}
