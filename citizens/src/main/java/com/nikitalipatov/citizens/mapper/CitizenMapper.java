package com.nikitalipatov.citizens.mapper;

import com.nikitalipatov.citizens.model.Citizen;
import com.nikitalipatov.common.dto.request.PersonDtoRequest;
import com.nikitalipatov.common.dto.response.CitizenWithPassportDto;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CitizenMapper {
    Citizen personDtoRequestToCitizen(PersonDtoRequest personDtoRequest);
    PersonDtoResponse citizenToPersonDtoResponse(Citizen citizen);
    CitizenWithPassportDto citizenToCitizenWithPassportDto(Citizen citizen, PassportDtoResponse passportDtoResponse);
    Citizen updateModel(PersonDtoRequest personDtoRequest, @MappingTarget Citizen citizen);
}
