package com.nikitalipatov.citizens.service.impl;

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

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository personRepository;
    private final PassportService passportService;
    private final HouseService houseService;
    private final PersonConverter converter;

    @Override
    public List<PersonHouseDto> findAllByStreet(String street) {
        var result = personRepository.findAllByStreet(street);
        return converter.toPersonHouseDto(result, street);
    }

    @Override
    public List<PersonPassportDto> getPassportDataByName(String personName) {
        var result = personRepository.findPassportDataByFullNameLike(personName);
        return converter.toPersonPassportDto(result);
    }

    @Override
    public List<PersonCarDto> getAllCarsByPersonName(String personName) {
        var result = personRepository.findCarsByFullName(personName);
        System.out.println(result);
        return converter.toPersonCarDto(result);
    }

    @Override
    public List<PersonHouseDto> getHousesByPersonName(String personName) {
        return converter.toPersonHouseDto(personRepository.findHousesByFullName(personName));
    }

    @Override
    public List<PersonDto> getAll() {
        return converter.toDto(personRepository.findAll());
    }

    @Override
    public PersonDto getByName(String name) {
        return converter.toDto(personRepository.findByFullName(name));
    }

    @Override
    @Transactional
    public PersonDto create(PersonRecord personRecord) {
        Citizen person = new Citizen(personRecord.fullName(), personRecord.age(),
                passportService.create(personRecord.passportRecord())
        );
        return converter.toDto(personRepository.save(person));
    }

    @Override
    @Transactional
    public PersonDto addHouse(int personId, int houseId) {
        Citizen person = getPerson(personId);
        House house = houseService.getHouse(houseId);
        Set<House> personHouses = person.getHouse();
        personHouses.add(house);
        person.setHouse(personHouses);
        return converter.toDto(personRepository.save(person));
    }

    @Override
    @Transactional
    public void delete(int personId) {
        Citizen person = getPerson(personId);
        passportService.delete(personId);
        person.setHouse(null);
        personRepository.deleteById(personId);
    }

    @Override
    @Transactional
    public PersonDto edit(int personId, PersonRecord personRecord) {
        Citizen person = getPerson(personId);
        return converter.toDto(personRepository.save(converter.toEntity(person, personRecord)));
    }

    @Override
    public Citizen getPerson(int personId) {
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("No such person with id " + personId)
        );
    }

}
