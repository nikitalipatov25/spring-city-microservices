package com.nikitalipatov.houses.service.impl;

import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.houses.converter.HouseConverter;
import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import com.nikitalipatov.houses.repository.HousePersonRepository;
import com.nikitalipatov.houses.repository.HouseRepository;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseConverter houseConverter;
    private final HousePersonRepository housePersonRepository;

    public void rollbackDeletedCitizenFromHouses(int personId, List<HouseDtoResponse> houseDtoResponseList) {
        houseDtoResponseList.forEach(house -> addCitizen(house.getHouseId(), personId));
    }

    @Override
    public List<HouseDtoResponse> getAll() {
        return houseConverter.toDto(houseRepository.findAll());
    }

    @Override
    public HouseDtoResponse create(HouseDtoRequest houseDtoRequest) {
        return houseConverter.toDto(houseRepository.save(houseConverter.toEntity(houseDtoRequest)));
    }

    public HousePersonDto addCitizen(int houseId, int personId) {
        HousePersonId housePersonId = new HousePersonId(houseId, personId);
        return houseConverter.toDto(housePersonRepository.save(new HousePerson(housePersonId)));
    }

    public void removePerson(int personId) {

        housePersonRepository.deleteByOwnerId(personId);
    }

    @Override
    public void delete(int houseId) {
        houseRepository.delete(getHouse(houseId));
    }

    @Override
    public HouseDtoResponse edit(int houseId, HouseDtoRequest houseDtoRequest) {
        var house = getHouse(houseId);
        return houseConverter.toDto(houseRepository.save(houseConverter.toEntityEdit(house, houseDtoRequest)));
    }

    @Override
    public House getHouse(int houseId) {
        return houseRepository.findById(houseId).orElseThrow(
                () -> new ResourceNotFoundException("No such house with id " + houseId)
        );
    }

    @Override
    public List<HouseDtoResponse> getPersonHouses(int ownerId) {
        return houseConverter.toDto(houseRepository.getHousesByOwnerId(ownerId));
    }
}
