package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.logs.LogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    public LogDto toLog(String logType, int numOfEntities) {
        return LogDto.builder()
                .logType(logType)
                .logEntity("citizen")
                .numOfEntities(numOfEntities)
                .time(SIMPLE_DATE_FORMAT.format(new Date()))
                .build();
    }

    public LogDto toLog(int numOfEntities) {
        return LogDto.builder()
                .logType("update")
                .logEntity("citizen")
                .numOfEntities(numOfEntities)
                .time(SIMPLE_DATE_FORMAT.format(new Date()))
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