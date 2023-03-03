package com.nikitalipatov.citizens.converter;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonConverter {

   // private final CarConverter carConverter;
   // private final HouseConverter houseConverter;
//    private final BankConverter bankConverter;

    public PersonDto toDto(Citizen person, PassportDto passportDto) {
        return PersonDto.builder()
                .name(person.getFullName())
                .sex(person.getSex())
                .age(person.getAge())
                .passportNumber(passportDto.getNumber())
                .passportSerial(passportDto.getSerial())
                .build();
    }

    public PersonDto toDto(Citizen person) {
        return PersonDto.builder()
                .name(person.getFullName())
                .sex(person.getSex())
                .age(person.getAge())
                .build();
    }

    public List<PersonDto> toDto(List<Citizen> personList) {
        var persons = new ArrayList<PersonDto>();
        for (Citizen citizen : personList) {
            persons.add(toDto(citizen));
        }
        return persons;
    }

//    public PersonPassportDto toPersonPassportDto(Citizen person, PassportDto passportDto) {
//        return PersonPassportDto.builder()
//                .name(person.getFullName())
//                .age(person.getAge())
//                .sex(person.getSex())
//                .passportNumber(person.getPassport().getNumber())
//                .passportSerial(person.getPassport().getSerial())
//                .sex(person.getPassport().getSex())
//                .age(person.getAge())
//                .build();
//    }

//    public List<PersonPassportDto> toPersonPassportDto(List<Citizen> personList) {
//        var persons = new ArrayList<PersonPassportDto>();
//        for (Citizen person : personList) {
//            persons.add(toPersonPassportDto(person));
//        }
//        return persons;
//    }
//
//    public PersonHouseDto toPersonHouseDto(Citizen person, String street) {
//        return PersonHouseDto.builder()
//                .name(person.getFullName())
//                .houses(houseConverter.toDto(person.getHouse().stream().filter(h -> h.getStreet().equals(street)).toList()))
//                .build();
//    }
//
//    public List<PersonHouseDto> toPersonHouseDto(List<Citizen> personList, String street) {
//        var persons = new ArrayList<PersonHouseDto>();
//        for (Citizen person : personList) {
//            persons.add(toPersonHouseDto(person, street));
//        }
//        return persons;
//    }
//
//    public PersonHouseDto toPersonHouseDto(Citizen person) {
//        return PersonHouseDto.builder()
//                .name(person.getFullName())
//                .houses(houseConverter.toDto(person.getHouse().stream().toList()))
//                .build();
//    }
//
//    public List<PersonHouseDto> toPersonHouseDto(List<Citizen> personList) {
//        var persons = new ArrayList<PersonHouseDto>();
//        for (Citizen person : personList) {
//            persons.add(toPersonHouseDto(person));
//        }
//        return persons;
//    }
//
//    public PersonCarDto toPersonCarDto(Citizen person) {
//        return PersonCarDto.builder()
//                .name(person.getFullName())
//                .carList(carConverter.toDto(person.getCar().stream().toList()))
//                .build();
//    }
//
//    public List<PersonCarDto> toPersonCarDto(List<Citizen> personList) {
//        var persons = new ArrayList<PersonCarDto>();
//        for (Citizen person : personList) {
//            persons.add(toPersonCarDto(person));
//        }
//        return persons;
//    }

    public Citizen toEntity(PersonRecord personRecord) {
        return Citizen.builder()
                .sex(personRecord.sex())
                .age(personRecord.age())
                .fullName(personRecord.fullName())
                .build();
    }

    public Citizen toEntityEdit(Citizen person, PersonRecord personRecord) {
        return person.toBuilder()
                .sex(personRecord.sex())
                .age(personRecord.age())
                .fullName(personRecord.fullName())
                .build();
    }

}