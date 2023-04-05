package com.nikitalipatov.passports.converter;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.enums.LogEntity;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.mapper.LogMapper;
import com.nikitalipatov.passports.mapper.PassportMapper;
import com.nikitalipatov.passports.model.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Component
@RequiredArgsConstructor
public class PassportConverter {

    private final PassportMapper passportMapper;

    public LogDto toLog(String logType, int numOfEntities) {
        return LogMapper.INSTANCE.toLogDto(logType, LogEntity.PASSPORT.name(), new Date(), numOfEntities);
    }

    public PassportDtoResponse toDto(Passport passport) {
        return passportMapper.passportToPassportDtoResponse(passport);
    }

    public List<PassportDtoResponse> toDto(List<Passport> passports) {
        List<PassportDtoResponse> passportDtoResponses = new ArrayList<>();
        for (Passport passport : passports) {
            passportDtoResponses.add(toDto(passport));
        }
        return passportDtoResponses;
    }

    public Passport toEntity(int personId) {
        return Passport.builder()
                .ownerId(personId)
                .serial((int) (Math.random() * (9999 - 1000) + 1000))
                .number((int) (Math.random() * (9999 - 1000) + 1000))
                .status(ModelStatus.ACTIVE.name())
                .build();
    }
}
