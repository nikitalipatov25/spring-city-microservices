package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
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
                .build();
    }

    public PersonDtoResponse toDto(Citizen person) {
        return PersonDtoResponse.builder()
                .name(person.getFullName())
                .sex(person.getSex())
                .age(person.getAge())
                .build();
    }

    public List<PersonDtoResponse> toDto(List<Citizen> personList) {
        var persons = new ArrayList<PersonDtoResponse>();
        for (Citizen citizen : personList) {
            persons.add(toDto(citizen));
        }
        return persons;
    }

    public Citizen toEntity(PersonDtoRequest personDtoRequest) {
        return Citizen.builder()
                .sex(personDtoRequest.getSex())
                .age(personDtoRequest.getAge())
                .fullName(personDtoRequest.getFullName())
                .build();
    }

    public Citizen toEntityEdit(Citizen person, PersonDtoRequest personDtoRequest) {
        return person.toBuilder()
                .sex(personDtoRequest.getSex())
                .age(personDtoRequest.getAge())
                .fullName(personDtoRequest.getFullName())
                .build();
    }

}