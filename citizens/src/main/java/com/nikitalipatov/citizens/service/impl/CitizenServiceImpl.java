package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.citizens.converter.PersonConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.request.KafkaStatus;
import com.nikitalipatov.common.dto.response.*;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.PassportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository personRepository;
    private final PersonConverter converter;
    private final PassportClient passportClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void rollback(PersonDtoResponse personDtoResponse) {
        personRepository.save(Citizen.builder()
                        .fullName(personDtoResponse.getName())
                        .age(personDtoResponse.getAge())
                        .sex(personDtoResponse.getSex())
                .build());
    }

    @Override
    public void rollbackCitizenCreation(int personId) {
        personRepository.delete(getPerson(personId));
    }

    @Override
      public List<PersonDtoResponse> getAll() {
        var persons = personRepository.findAll();
        List<Integer> ownerIds = persons.stream().map(Citizen::getId).collect(Collectors.toList());
        List<PassportDtoResponse> passports = passportClient.getPassportsByOwnerIds(ownerIds);
        List<PersonDtoResponse> personDtoResponses = new ArrayList<>();
        persons.forEach(person -> {
            PassportDtoResponse passport = passports.get(persons.indexOf(person));
            personDtoResponses.add(converter.toDto(person, passport));
        });
        return personDtoResponses;
    }

    @Override
    public PersonDtoResponse getByName(String name) {
        return converter.toDto(personRepository.findByFullName(name));
    }

    @Override
    public PersonDtoResponse create(PersonDtoRequest personDtoRequest) {
        Citizen person = personRepository.save(converter.toEntity(personDtoRequest));
        kafkaTemplate.send("personEvents", new PersonCreationDto(KafkaStatus.SUCCESS, person.getId()));
        return converter.toDto(person);
    }

    @Override
    public void delete(int personId) {
        Citizen person = getPerson(personId);
        var result = PersonDeleteDto.builder()
                .person(converter.toDto(person))
                .build();
        try {
            personRepository.deleteById(personId);
            result.setPersonDeleteStatus(KafkaStatus.SUCCESS);
            kafkaTemplate.send("personEvents", result);
        } catch (Exception e) {
            personRepository.deleteById(personId);
            result.setPersonDeleteStatus(KafkaStatus.FAIL);
            kafkaTemplate.send("personEvents", result);
        }
    }

    @Override
    public PersonDtoResponse edit(int personId, PersonDtoRequest personDtoRequest) {
        Citizen person = getPerson(personId);
        return converter.toDto(personRepository.save(converter.toEntityEdit(person, personDtoRequest)));
    }

    @Override
    public Citizen getPerson(int personId) {
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such person with id " + personId)
        );
    }

}
