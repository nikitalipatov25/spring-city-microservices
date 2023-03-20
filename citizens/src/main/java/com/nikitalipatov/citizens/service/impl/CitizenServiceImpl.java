package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.citizens.converter.PersonConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.PassportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final KafkaTemplate<java.lang.String, KafkaMessage> kafkaTemplate;
    private final CitizenRepository personRepository;
    private final PersonConverter citizenConverter;
    private final PassportClient passportClient;

    @Override
    public void rollback(int citizenId, EventType eventType) {
        Citizen citizen = getPerson(citizenId);
        if (citizen.getStatus().equals(ModelStatus.DELETED.name())) {
            citizen.setStatus(ModelStatus.ACTIVE.name());
            personRepository.save(citizen);
        }
        var message = new KafkaMessage(
                UUID.randomUUID(),
                Status.SUCCESS,
                eventType,
                citizenId
        );
       kafkaTemplate.send("citizenCommand", message);
    }

    @Override
    public void rollbackCitizenCreation(int personId) {
        personRepository.delete(getPerson(personId));
    }

    @Override
    public List<PersonDtoResponse> getAll() {
        var persons = personRepository.findAllActive();
        List<Integer> ownerIds = persons.stream().map(Citizen::getId).collect(Collectors.toList());
        List<PassportDtoResponse> passports = passportClient.getPassportsByOwnerIds(ownerIds);
        List<PersonDtoResponse> personDtoResponses = new ArrayList<>();
        persons.forEach(person -> {
            PassportDtoResponse passport = passports.get(persons.indexOf(person));
            personDtoResponses.add(citizenConverter.toDto(person, passport));
        });
        return personDtoResponses;
    }

    @Override
    public PersonDtoResponse getByName(java.lang.String name) {
        return citizenConverter.toDto(personRepository.findByFullName(name));
    }

    @Override
    @Transactional
    public PersonDtoResponse create(PersonDtoRequest personDtoRequest) {
        Citizen person = personRepository.save(citizenConverter.toEntity(personDtoRequest));
        var message = new KafkaMessage(
                UUID.randomUUID(),
                Status.SUCCESS,
                EventType.CITIZEN_CREATED,
                person.getId()
        );
        kafkaTemplate.send("citizenCommand", message);
        return citizenConverter.toDto(person);
    }

    @Override
    public void delete(int personId) {
        Citizen person = getPerson(personId);
        person.setStatus(ModelStatus.DELETED.name());
        personRepository.save(person);
        var message = new KafkaMessage(
                UUID.randomUUID(),
                Status.SUCCESS,
                EventType.CITIZEN_DELETED,
                personId
        );
        kafkaTemplate.send("citizenCommand", message);
    }

    @Override
    public PersonDtoResponse edit(int personId, PersonDtoRequest personDtoRequest) {
        Citizen person = getPerson(personId);
        return citizenConverter.toDto(personRepository.save(citizenConverter.toEntity(person, personDtoRequest)));
    }

    @Override
    public Citizen getPerson(int personId) {
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such person with id " + personId)
        );
    }

}
