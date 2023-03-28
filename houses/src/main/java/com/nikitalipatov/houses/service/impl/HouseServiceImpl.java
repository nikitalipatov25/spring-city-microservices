package com.nikitalipatov.houses.service.impl;

import com.nikitalipatov.common.dto.kafka.KafkaMessage;
import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.request.HouseDtoRequest;
import com.nikitalipatov.common.dto.response.HousePersonDto;
import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.common.error.ResourceNotFoundException;
import com.nikitalipatov.houses.converter.HouseConverter;
import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.houses.model.HousePerson;
import com.nikitalipatov.houses.repository.HousePersonRepository;
import com.nikitalipatov.houses.repository.HouseRepository;
import com.nikitalipatov.houses.service.HouseService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseConverter houseConverter;
    private final HousePersonRepository housePersonRepository;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final StompSession stompSession;

    private static int numberOfHouses;

    @PostConstruct
    public void init() {
        numberOfHouses = houseRepository.countActiveHouses();
    }

    @Scheduled(fixedDelay = 300000)
    public void updateNumOfCars() {
        stompSession.send("/app/logs", houseConverter.toLog(numberOfHouses));
    }

    public void rollbackDeletedCitizenFromHouses(int personId) {
        var personHouses = housePersonRepository.getCitizenHouses(personId);
        personHouses.forEach(house -> house.setStatus(ModelStatus.ACTIVE.name()));
        housePersonRepository.saveAll(personHouses);
        stompSession.send("/app/logs", houseConverter.toLog("create", 1));
    }

    @Override
    public List<HouseDtoResponse> getAll() {
        return houseConverter.toDto(houseRepository.findAllActive());
    }

    @Override
    public HouseDtoResponse create(HouseDtoRequest houseDtoRequest) {
        stompSession.send("/app/logs", houseConverter.toLog("create", 1));
        return houseConverter.toDto(houseRepository.save(houseConverter.toEntity(houseDtoRequest)));
    }

    public HousePersonDto addCitizen(int houseId, int personId) {
        HousePersonId housePersonId = new HousePersonId(houseId, personId);
        return houseConverter.toDto(housePersonRepository.save(HousePerson.builder()
                .housePersonId(housePersonId)
                .status(ModelStatus.ACTIVE.name())
                .build()));
    }

    @Transactional
    public void removePerson(int personId) {
        try {
            var personHouses = housePersonRepository.getCitizenHouses(personId);
            personHouses.forEach(house -> house.setStatus(ModelStatus.DELETED.name()));
            housePersonRepository.saveAll(personHouses);
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.SUCCESS,
                    EventType.HOUSE_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        } catch (Exception e) {
            var message = new KafkaMessage(
                    UUID.randomUUID(),
                    Status.FAIL,
                    EventType.HOUSE_DELETED,
                    personId
            );
            kafkaTemplate.send("result", message);
        }
    }

    @Override
    public void delete(int houseId) {
        houseRepository.delete(getHouse(houseId));
        stompSession.send("/app/logs", houseConverter.toLog("delete", 1));
    }

    @Override
    public HouseDtoResponse edit(int houseId, HouseDtoRequest houseDtoRequest) {
        var house = getHouse(houseId);
        return houseConverter.toDto(houseRepository.save(houseConverter.toEntity(house, houseDtoRequest)));
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
