package com.nikitalipatov.citizens.service;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;

import java.util.List;

public interface CitizenService {

    void rollback(PersonDtoResponse personDtoResponse);

    List<PersonDtoResponse> getAll();

    PersonDtoResponse getByName(String name);

    PersonDtoResponse create(PersonDtoRequest personDtoRequest);

    void delete(int id);

    void rollbackCitizenCreation(int personId);

    PersonDtoResponse edit(int id, PersonDtoRequest personDtoRequest);

    Citizen getPerson(int personId);

}
