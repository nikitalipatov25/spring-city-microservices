package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.citizens.converter.PersonConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.PassportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository personRepository;
    private final PersonConverter converter;
    private final RestTemplate restTemplate;
    private final PassportClient passportClient;

    @Override
      public List<PersonDtoResponse> getAll() {
        var persons = personRepository.findAll();
        List<Integer> ownerIds = persons.stream().map(Citizen::getId).collect(Collectors.toList());
        List<PassportDtoResponse> passportDtoResponses = passportClient.getPassportsByOwnerIds(ownerIds);
        List<PersonDtoResponse> personDtoResponses = new ArrayList<>();
        PersonDtoResponse person;
        for (int i = 0; i < persons.size(); i++) {
            person = converter.toDto(persons.get(i), Objects.requireNonNull(passportDtoResponses).get(i));
            personDtoResponses.add(person);
        }
        return personDtoResponses;
    }

    @Override
    public PersonDtoResponse getByName(String name) {
        return converter.toDto(personRepository.findByFullName(name));
    }

    @Override
       public PersonDtoResponse create(PersonDtoRequest personDtoRequest) {
        Citizen person = personRepository.save(converter.toEntity(personDtoRequest));
        var passport = passportClient.create(person.getId());
        return converter.toDto(person, Objects.requireNonNull(passport));
    }

    @Override
    @Transactional
    public void delete(int personId) {
        // todo Если например в сервисе домов что то пойдет не так и вернется ошибка, то транзакция тебя тут не спасет и отката в сервисе
        //  домов и паспортов не произойдет, в результате ты получишь не консистентные данные, что обычно весьма критично для бизнеса.
        //  Вообще этот метод самый сложный во всем проекте, нужно подумать как сделать так, что бы если какой то из сервисов недоступен
        //  у нас не ломалась логика
        restTemplate.delete("http://localhost:8080/api/passport/delete/{personId}", personId);
        restTemplate.delete("http://localhost:8080/api/car/delete/person/{personId}", personId);
        restTemplate.delete("http://localhost:8080/api/house/delete/person/{personId}", personId);
        personRepository.deleteById(personId);
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
