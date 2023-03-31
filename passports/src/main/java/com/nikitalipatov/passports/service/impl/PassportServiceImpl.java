package com.nikitalipatov.passports.service.impl;

import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.LogType;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.passports.converter.PassportConverter;
import com.nikitalipatov.passports.model.Passport;
import com.nikitalipatov.passports.repository.PassportRepository;
import com.nikitalipatov.passports.service.PassportService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportConverter passportConverter;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final StompSession stompSession;

    private static final AtomicInteger numberOfPassports = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        numberOfPassports.set(passportRepository.countActivePassports());
    }

    @Scheduled(fixedDelay = 20000)
    public void updateNumOfPassport() {
        stompSession.send("/app/logs", passportConverter.toLog(LogType.UPDATE.name(), numberOfPassports.get()));
    }

    @Override
    public void rollbackDeletedPassport(int ownerId) {
        Passport passport = getPassport(ownerId);
        passport.setStatus(ModelStatus.ACTIVE.name());
        passportRepository.save(passport);
        numberOfPassports.getAndIncrement();
        stompSession.send("/app/logs", passportConverter.toLog(LogType.CREATE.name(), numberOfPassports.get()));
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
            numberOfPassports.getAndIncrement();
            stompSession.send("/app/logs", passportConverter.toLog(LogType.CREATE.name(), numberOfPassports.get()));
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
        return passportConverter.toDto(passportRepository.findAllByStatusAndOwnerIdIn("ACTIVE", ownerIds));
    }

    public PassportDtoResponse getByOwnerId(int personId) {
        return passportConverter.toDto(getPassport(personId));
    }


    @Override
    @Transactional
    public void delete(int personId) {
        try {
            Passport passport = getPassport(personId);
            passport.setStatus(ModelStatus.DELETED.name());
            passportRepository.save(passport);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.PASSPORT_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
            numberOfPassports.getAndDecrement();
            stompSession.send("/app/logs", passportConverter.toLog(LogType.DELETE.name(), numberOfPassports.get()));
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
