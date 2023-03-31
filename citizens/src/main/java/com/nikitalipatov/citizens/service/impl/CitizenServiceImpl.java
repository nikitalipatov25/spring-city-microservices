package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.citizens.converter.PersonConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenWithPassportDto;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.LogType;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.PassportClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class CitizenServiceImpl implements CitizenService {

    private static final AtomicInteger numberOfCitizens = new AtomicInteger(0);

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final CitizenRepository personRepository;
    private final PersonConverter citizenConverter;
    private final PassportClient passportClient;
    private final StompSession stompSession;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @PostConstruct
    public void init() {
        numberOfCitizens.set(personRepository.countActiveCitizens());
    }

    @Scheduled(fixedDelay = 20000)
    public void updateNumOfCitizens() {
        stompSession.send("/app/logs", citizenConverter.toLog(LogType.UPDATE.name(), numberOfCitizens.get()));
    }

    @Scheduled(fixedDelay = 10000)
    public void cloneFactory() {
        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                create(PersonDtoRequest.builder().build());
            }
        });
    }

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
        numberOfCitizens.getAndDecrement();
    }

    @Override
    public List<CitizenWithPassportDto> getAll() {
        var persons = personRepository.findAllActive();
        var a = persons.get(0);
        List<Integer> ownerIds = persons.stream().map(Citizen::getId).collect(Collectors.toList());
        List<PassportDtoResponse> passports = passportClient.getPassportsByOwnerIds(ownerIds);
        List<CitizenWithPassportDto> personDtoResponses = new ArrayList<>();
        persons.forEach(person -> {
            PassportDtoResponse passport = passports.get(persons.indexOf(person));
            personDtoResponses.add(citizenConverter.toDto(person, passport));
        });
        return personDtoResponses;
    }

    @Override
    public PersonDtoResponse getByName(String name) {
        return citizenConverter.toDto(personRepository.findByFullName(name));
    }

    @Override
    @Transactional
    public PersonDtoResponse create(PersonDtoRequest personDtoRequest) {
        Citizen person = personRepository.save(citizenConverter.toEntity(personDtoRequest));
        numberOfCitizens.getAndIncrement();
        var message = new KafkaMessage(
                UUID.randomUUID(),
                Status.SUCCESS,
                EventType.CITIZEN_CREATED,
                person.getId()
        );
        kafkaTemplate.send("citizenCommand", message);
        stompSession.send("/app/logs", citizenConverter.toLog(LogType.CREATE.name(), numberOfCitizens.get()));
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
        stompSession.send("/app/logs", citizenConverter.toLog("delete", 1));
        numberOfCitizens.getAndDecrement();
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

    @Override
    public int getNumOfActiveCitizens() {
        return personRepository.countActiveCitizens();
    }

}
