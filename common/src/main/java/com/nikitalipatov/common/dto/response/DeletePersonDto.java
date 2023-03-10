package com.nikitalipatov.common.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletePersonDto {
    private PersonDtoResponse person;
    private PassportDtoResponse passport;
    private String passportDeleteStatus;
    private List<CarDtoResponse> carList;
    private String carDeleteStatus;
    private List<HouseDtoResponse> houseLst;
    private String houseDeleteStatus;

    public DeletePersonDto(PersonDtoResponse person) {
        this.person = person;
    }

    public DeletePersonDto(List<CarDtoResponse> carList) {
        this.carList = carList;
    }
}
