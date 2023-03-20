package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.ModelStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonConverter {

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