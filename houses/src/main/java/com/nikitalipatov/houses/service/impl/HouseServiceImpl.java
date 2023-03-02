package com.nikitalipatov.houses.service.impl;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.citizens.repository.CitizenRepository;
import com.nikitalipatov.common.converter.HouseConverter;
import com.nikitalipatov.common.dto.HouseDto;
import com.nikitalipatov.common.dto.HouseRecord;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.houses.converter.HouseConverter;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.repository.HouseRepository;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    @Lazy
    private final CitizenRepository personRepository;
    private final HouseConverter converter;

    @Override
    public List<HouseDto> getAll() {
        return converter.toDto(houseRepository.findAll());
    }

    @Override
    @Transactional
    public HouseDto create(HouseRecord houseRecord) {
        House house = new House(houseRecord.city(), houseRecord.street(), houseRecord.number());
        return converter.toDto(houseRepository.save(house));
    }

    @Override
    @Transactional
    public void delete(int houseId) {
        var house = getHouse(houseId);
        var persons = personRepository.findAllByHouseIn(houseId);
        for (Citizen person : persons) {
            person.getHouse().removeIf(h -> h.getId() == houseId);
        }
        personRepository.saveAll(persons);
        houseRepository.delete(house);
    }

    @Override
    @Transactional
    public HouseDto edit(int houseId, HouseRecord houseRecord) {
        var house = getHouse(houseId);
        return converter.toDto(houseRepository.save(converter.toEntity(house, houseRecord)));
    }

    @Override
    public House getHouse(int houseId) {
        return houseRepository.findById(houseId).orElseThrow(
                () -> new ResourceNotFoundException("No such house with id " + houseId)
        );
    }
}
