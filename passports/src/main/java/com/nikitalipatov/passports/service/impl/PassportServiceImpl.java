package com.nikitalipatov.passports.service.impl;

import com.nikitalipatov.common.dto.kafka.CitizenEvent;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.passports.converter.PassportConverter;
import com.nikitalipatov.passports.model.Passport;
import com.nikitalipatov.passports.repository.PassportRepository;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportConverter passportConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;


    @Override
    public void rollbackDeletedPassport(int ownerId) {
        Passport passport = getPassport(ownerId);
        passport.setStatus(ModelStatus.ACTIVE);
        passportRepository.save(passport);
    }

    @Override
    public PassportDtoResponse create(int personId) {
        Passport passport= passportConverter.toEntity(personId);
        try {
            passportRepository.save(passport);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.PASSPORT_CREATED,
                    personId
            );
            kafkaTemplate.send("result", message);
        } catch (Exception e) {
            passportRepository.save(passport);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.FAIL,
                    EventType.PASSPORT_CREATED,
                    personId
            );
            kafkaTemplate.send("result", message);
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
    @Transactional
    public void delete(int personId) {
        try {
            Passport passport = getPassport(personId);
            passport.setStatus(ModelStatus.DELETED);
            passportRepository.save(passport);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.PASSPORT_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        } catch (Exception e) {
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.FAIL,
                    EventType.PASSPORT_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        }
    }

    public Passport getPassport(int personId) {
        return passportRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such passport with owner id " + personId)
        );
    }
}
