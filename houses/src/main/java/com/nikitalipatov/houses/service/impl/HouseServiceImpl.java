package com.nikitalipatov.houses.service.impl;

import com.nikitalipatov.common.dto.HouseDto;
import com.nikitalipatov.common.dto.HouseRecord;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.houses.converter.HouseConverter;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import com.nikitalipatov.houses.repository.HousePersonRepository;
import com.nikitalipatov.houses.repository.HouseRepository;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseConverter houseConverter;
    private final HousePersonRepository housePersonRepository;

    @Override
    public List<HouseDto> getAll() {
        return houseConverter.toDto(houseRepository.findAll());
    }

    @Override
    @Transactional
    public HouseDto create(HouseRecord houseRecord) {
        return houseConverter.toDto(houseRepository.save(houseConverter.toEntity(houseRecord)));
    }

    public HouseDto addCitizen(int houseId, int personId) {
        var house = getHouse(houseId);
        List<HousePerson> housePeople = house.getHousePerson();
        housePeople.add(housePersonRepository.save(houseConverter.toEntity(houseId, personId)));
        house.setHousePerson(housePeople);
        return houseConverter.toDto(houseRepository.save(house));
    }

    public void removePerson(int personId) {
        housePersonRepository.deleteAllByPersonId(personId);
    }

    @Override
    @Transactional
    public void delete(int houseId) {
        houseRepository.delete(getHouse(houseId));
    }

    @Override
    @Transactional
    public HouseDto edit(int houseId, HouseRecord houseRecord) {
        var house = getHouse(houseId);
        return houseConverter.toDto(houseRepository.save(houseConverter.toEntityEdit(house, houseRecord)));
    }

    @Override
    public House getHouse(int houseId) {
        return houseRepository.findById(houseId).orElseThrow(
                () -> new ResourceNotFoundException("No such house with id " + houseId)
        );
    }
}
