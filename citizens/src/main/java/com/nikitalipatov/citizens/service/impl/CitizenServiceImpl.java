package com.nikitalipatov.citizens.service.impl;

import com.nikitalipatov.cars.service.CarService;
import com.nikitalipatov.citizens.converter.PersonConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.citizens.service.CitizenService;
import com.nikitalipatov.common.dto.*;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.service.HouseService;
import com.nikitalipatov.passports.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository personRepository;
    //private final CarService carService;
    private final PersonConverter converter;
    //private final HouseService houseService;
    //private final PassportService passportService;
    private final RestTemplate restTemplate;



//    @Override
//    public List<PersonHouseDto> findAllByStreet(String street) {
//        var result = personRepository.findAllByStreet(street);
//        return converter.toPersonHouseDto(result, street);
//    }
//
//    @Override
//    public List<PersonPassportDto> getPassportDataByName(String personName) {
//        var result = personRepository.findPassportDataByFullNameLike(personName);
//        return converter.toPersonPassportDto(result);
//    }
//
//    @Override
//    public List<PersonCarDto> getAllCarsByPersonName(String personName) {
//        var result = personRepository.findCarsByFullName(personName);
//        System.out.println(result);
//        return converter.toPersonCarDto(result);
//    }
//
//    @Override
//    public List<PersonHouseDto> getHousesByPersonName(String personName) {
//        return converter.toPersonHouseDto(personRepository.findHousesByFullName(personName));
//    }

    @Override
    @Transactional
    public List<PersonDto> getAll() {
        var persons = personRepository.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        PersonDto person;
        for (int i = 0; i < persons.size(); i++) {
            PassportDto passport =
                    restTemplate.getForObject("http://localhost:8080/api/passport/get/{personId}", PassportDto.class, persons.get(i).getId());
            person = converter.toDto(persons.get(i), Objects.requireNonNull(passport));
            personDtos.add(person);
        }
        return personDtos;
    }

    @Override
    public PersonDto getByName(String name) {
        return converter.toDto(personRepository.findByFullName(name));
    }

    @Override
    @Transactional
    public PersonDto create(PersonRecord personRecord) {
        Citizen person = personRepository.save(converter.toEntity(personRecord));
        var passport = restTemplate.postForObject("http://localhost:8080/api/passport/create", person.getId(), PassportDto.class);
        return converter.toDto(person, Objects.requireNonNull(passport));
    }

//    @Override
//    @Transactional
//    public PersonDto addHouse(int personId, int houseId) {
//        Citizen person = getPerson(personId);
//        House house = houseService.getHouse(houseId);
//        Set<House> personHouses = person.getHouse();
//        personHouses.add(house);
//        person.setHouse(personHouses);
//        return converter.toDto(personRepository.save(person));
//    }

    @Override
    @Transactional
    public void delete(int personId) {
        //passportService.delete(personId);
        restTemplate.delete("http://localhost:8080/api/passport/delete/{personId}", personId);
        //carService.deletePersonCars(personId);
        restTemplate.delete("http://localhost:8080/api/car/delete/person/{personId}", personId);
        //houseService.removePerson(personId);
        restTemplate.delete("http://localhost:8080/api/house/delete/person/{personId}", personId);
        personRepository.deleteById(personId);
        //delete passport
        //delete person cars
        //delete from houses
    }

    @Override
    @Transactional
    public PersonDto edit(int personId, PersonRecord personRecord) {
        Citizen person = getPerson(personId);
        return converter.toDto(personRepository.save(converter.toEntityEdit(person, personRecord)));
    }

    @Override
    public Citizen getPerson(int personId) {
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such person with id " + personId)
        );
    }

}
