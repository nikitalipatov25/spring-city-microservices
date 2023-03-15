package com.nikitalipatov.passports.service.impl;

import com.nikitalipatov.common.dto.kafka.PassportCreateDto;
import com.nikitalipatov.common.dto.kafka.PassportDeleteStatus;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.dto.kafka.CitizenCreateDto;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.kafka.KafkaObject;
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
    private final KafkaTemplate<String, KafkaObject> kafkaTemplate;

//    @Override
//    public void rollbackDeletedPassport(PassportDtoResponse passportDtoResponse) {
//        passportRepository.save(new Passport(
//                passportDtoResponse.getOwnerId(),
//                passportDtoResponse.getSerial(),
//                passportDtoResponse.getNumber()
//        ));
//    }

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
            kafkaTemplate.send("passportSender", PassportCreateDto.builder()
                    .passportCreateStatus(KafkaStatus.SUCCESS)
                    .ownerId(personId)
                    .build());
        } catch (Exception e) {
            kafkaTemplate.send("passportSender", PassportCreateDto.builder()
                    .passportCreateStatus(KafkaStatus.FAIL)
                    .ownerId(personId)
                    .build());
        }
        return passportConverter.toDto(passport);
    }

    public List<PassportDtoResponse> getAllByOwnerIds(List<Integer> ownerIds) {
        return passportConverter.toDto(passportRepository.findAllByOwnerIdIn(ownerIds));
    }

    public PassportDtoResponse getByOwnerId(int personId) {
        return passportConverter.toDto(getPassport(personId));
    }

//    @Override
//    public void delete(int personId) {
//        Passport passport = getPassport(personId);
//        try {
//            passportRepository.deleteByOwnerId(personId);
//            kafkaTemplate.send("passportSender", PassportDeleteStatus.builder()
//                    .passport(passportConverter.toDto(passport))
//                    .passportDeleteStatus(KafkaStatus.SUCCESS)
//                    .build());
//        } catch (Exception e) {
//            kafkaTemplate.send("passportSender", PassportDeleteStatus.builder()
//                    .passportDeleteStatus(KafkaStatus.SUCCESS)
//                    .build());
//        }
//    }

    @Override
    public void delete(int personId) {
        Passport passport = getPassport(personId);
        try {
            passport.setStatus(ModelStatus.PREPARING);
            passportRepository.save(passport);
            kafkaTemplate.send("passportSender", PassportDeleteStatus.builder()
                    .passport(passportConverter.toDto(passport))
                    .passportDeleteStatus(KafkaStatus.SUCCESS)
                    .build());
        } catch (Exception e) {
            kafkaTemplate.send("passportSender", PassportDeleteStatus.builder()
                    .passportDeleteStatus(KafkaStatus.SUCCESS)
                    .build());
        }
    }

    public Passport getPassport(int personId) {
        return passportRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such passport with owner id " + personId)
        );
    }
}
