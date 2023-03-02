package com.nikitalipatov.citizens.service;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.*;

import java.util.List;

public interface CitizenService {

    List<PersonDto> getAll();

    PersonDto getByName(String name);

    PersonDto create(PersonRecord personRecord);

    PersonDto addHouse(int personId, int houseId);

    void delete(int id);

    PersonDto edit(int id, PersonRecord personRecord);

    List<PersonCarDto> getAllCarsByPersonName(String personName);

    List<PersonPassportDto> getPassportDataByName(String personName);

    List<PersonHouseDto> findAllByStreet(String street);

    List<PersonHouseDto> getHousesByPersonName(String personName);

    Citizen getPerson(int personId);

}
