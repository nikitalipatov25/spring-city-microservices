package com.nikitalipatov.citizens.service;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenWithPassportDto;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.EventType;

import java.util.List;

public interface CitizenService {

    List<CitizenWithPassportDto> getAll();

    PersonDtoResponse getByName(String name);

    PersonDtoResponse create(PersonDtoRequest personDtoRequest);

    void delete(int id);

    void rollbackCitizenCreation(int personId);

    PersonDtoResponse edit(int id, PersonDtoRequest personDtoRequest);

    Citizen getPerson(int personId);

    void rollback(int citizenId, EventType eventType);

    int getNumOfActiveCitizens();

}
