package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.citizens.converter.PersonConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.PersonCreationDto;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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
    private final HouseClient houseClient;
    private final CarClient carClient;

    //private final KafkaProperties kafkaProperties;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    //private final KafkaTemplate<String, DeletePersonDto> kafkaTemplateDelete;

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
        kafkaTemplate.send("personEvents", new PersonCreationDto("ok", person.getId()));
        return converter.toDto(person);
    }

    @Override
    public void delete(int personId) {
        // todo Если например в сервисе домов что то пойдет не так и вернется ошибка, то транзакция тебя тут не спасет и отката в сервисе
        //  домов и паспортов не произойдет, в результате ты получишь не консистентные данные, что обычно весьма критично для бизнеса.
        //  Вообще этот метод самый сложный во всем проекте, нужно подумать как сделать так, что бы если какой то из сервисов недоступен
        //  у нас не ломалась логика

        Citizen person = getPerson(personId);

        var res = DeletePersonDto.builder()
                .person(converter.toDto(person))
                .build();

        personRepository.deleteById(personId);

        kafkaTemplate.send("personEvents", res);
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
