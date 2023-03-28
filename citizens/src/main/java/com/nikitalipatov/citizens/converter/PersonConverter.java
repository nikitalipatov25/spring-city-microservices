package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.logs.MyLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public MyLog toLog(String logType, int numOfEntities) {
        return MyLog.builder()
                .logType(logType)
                .logEntity("citizen")
                .numOfEntities(numOfEntities)
                .time(simpleDateFormat.format(new Date()))
                .build();
    }

    public MyLog toLog(int numOfEntities) {
        return MyLog.builder()
                .logType("update")
                .logEntity("citizen")
                .numOfEntities(numOfEntities)
                .time(simpleDateFormat.format(new Date()))
                .build();
    }

    public PersonDtoResponse toDto(Citizen person, PassportDtoResponse passportDtoResponse) {
        return PersonDtoResponse.builder()
                .name(person.getFullName())
                .sex(person.getSex())
                .age(person.getAge())
                .passportNumber(passportDtoResponse.getNumber())
                .passportSerial(passportDtoResponse.getSerial())
                .personId(person.getId())
                .build();
    }

    public PersonDtoResponse toDto(Citizen person) {
        return PersonDtoResponse.builder()
                .name(person.getFullName())
                .sex(person.getSex())
                .age(person.getAge())
                .personId(person.getId())
                .build();
    }

    public Citizen toEntity(PersonDtoRequest personDtoRequest) {
        return Citizen.builder()
                .sex(personDtoRequest.getSex())
                .age(personDtoRequest.getAge())
                .fullName(personDtoRequest.getFullName())
                .status(ModelStatus.ACTIVE.name())
                .build();
    }

    public Citizen toEntity(Citizen person, PersonDtoRequest personDtoRequest) {
        return person.toBuilder()
                .sex(personDtoRequest.getSex())
                .age(personDtoRequest.getAge())
                .fullName(personDtoRequest.getFullName())
                .status(ModelStatus.ACTIVE.name())
                .build();
    }

}