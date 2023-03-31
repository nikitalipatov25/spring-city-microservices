package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.citizens.mapper.CitizenMapper;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenWithPassportDto;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.LogEntity;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    private final CitizenMapper citizenMapper;

    public LogDto toLog(String logType, int numOfEntities) {
        return  LogMapper.INSTANCE.toLogDto(logType, LogEntity.CITIZEN.name(), new Date(), numOfEntities);
    }

    public CitizenWithPassportDto toDto(Citizen person, PassportDtoResponse passportDtoResponse) {
        return citizenMapper.citizenToCitizenWithPassportDto(person, passportDtoResponse);
    }

    public PersonDtoResponse toDto(Citizen person) {
        return citizenMapper.citizenToPersonDtoResponse(person);
    }

    public Citizen toEntity(PersonDtoRequest personDtoRequest) {
        return citizenMapper.personDtoRequestToCitizen(personDtoRequest);
    }

    public Citizen toEntity(Citizen person, PersonDtoRequest personDtoRequest) {
        return citizenMapper.updateModel(personDtoRequest, person);
    }

}