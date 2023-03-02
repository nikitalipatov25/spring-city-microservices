package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.cars.converter.CarConverter;
import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.converter.CarConverter;
import com.nikitalipatov.common.converter.HouseConverter;
import com.nikitalipatov.common.dto.*;
import com.nikitalipatov.houses.converter.HouseConverter;
import com.nikitalipatov.passports.model.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    private final CarConverter carConverter;
    private final HouseConverter houseConverter;
//    private final BankConverter bankConverter;

    public PersonDto toDto(Citizen person) {
        return PersonDto.builder()
                .name(person.getFullName())
                .number(person.getPassport().getNumber())
                .serial(person.getPassport().getSerial())
                .sex(person.getPassport().getSex())
                .age(person.getAge())
                .car(person.getCar() != null ? carConverter.toDto(person.getCar().stream().toList()) : null) // todo List.copyOf()
                .house(person.getHouse() != null ? houseConverter.toDto(person.getHouse().stream().toList()) : null)
//                .bank(person.getBankAccount() != null ? bankConverter.toDto(person.getBankAccount().stream().toList()) : null)
                .build();
    }

    public List<PersonDto> toDto(List<Citizen> personList) {
        var persons = new ArrayList<PersonDto>();
        for (Citizen person : personList) {
            persons.add(toDto(person));
        }
        return persons;
    }

    public PersonPassportDto toPersonPassportDto(Citizen person) {
        return PersonPassportDto.builder()
                .name(person.getFullName())
                .passportNumber(person.getPassport().getNumber())
                .passportSerial(person.getPassport().getSerial())
                .sex(person.getPassport().getSex())
                .age(person.getAge())
                .build();
    }

    public List<PersonPassportDto> toPersonPassportDto(List<Citizen> personList) {
        var persons = new ArrayList<PersonPassportDto>();
        for (Citizen person : personList) {
            persons.add(toPersonPassportDto(person));
        }
        return persons;
    }

    public PersonHouseDto toPersonHouseDto(Citizen person, String street) {
        return PersonHouseDto.builder()
                .name(person.getFullName())
                .houses(houseConverter.toDto(person.getHouse().stream().filter(h -> h.getStreet().equals(street)).toList()))
                .build();
    }

    public List<PersonHouseDto> toPersonHouseDto(List<Citizen> personList, String street) {
        var persons = new ArrayList<PersonHouseDto>();
        for (Citizen person : personList) {
            persons.add(toPersonHouseDto(person, street));
        }
        return persons;
    }

    public PersonHouseDto toPersonHouseDto(Citizen person) {
        return PersonHouseDto.builder()
                .name(person.getFullName())
                .houses(houseConverter.toDto(person.getHouse().stream().toList()))
                .build();
    }

    public List<PersonHouseDto> toPersonHouseDto(List<Citizen> personList) {
        var persons = new ArrayList<PersonHouseDto>();
        for (Citizen person : personList) {
            persons.add(toPersonHouseDto(person));
        }
        return persons;
    }

    public PersonCarDto toPersonCarDto(Citizen person) {
        return PersonCarDto.builder()
                .name(person.getFullName())
                .carList(carConverter.toDto(person.getCar().stream().toList()))
                .build();
    }

    public List<PersonCarDto> toPersonCarDto(List<Citizen> personList) {
        var persons = new ArrayList<PersonCarDto>();
        for (Citizen person : personList) {
            persons.add(toPersonCarDto(person));
        }
        return persons;
    }

    public Citizen toEntity(Citizen person, PersonRecord personRecord) {
        person.setFullName(personRecord.fullName());
        person.setAge(personRecord.age());

        Passport passport = person.getPassport(); // todo toBuilder()
        passport.setAddress(personRecord.passportRecord().address());
        passport.setAddressFact(personRecord.passportRecord().addressFact());
        passport.setDateOfBirth(personRecord.passportRecord().dateOfBirth());
        passport.setIssued(personRecord.passportRecord().issued());
        passport.setIssuedBy(personRecord.passportRecord().issuedBy());
        passport.setNumber(personRecord.passportRecord().number());
        passport.setSerial(personRecord.passportRecord().serial());
        passport.setSex(personRecord.passportRecord().sex());
        passport.setPlaceOfBirth(personRecord.passportRecord().placeOfBirth());

        person.setPassport(passport);
        return person;
    }

}