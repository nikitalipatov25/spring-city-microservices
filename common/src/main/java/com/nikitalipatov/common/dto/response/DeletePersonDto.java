package com.nikitalipatov.common.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DeletePersonDto {
    private PersonDtoResponse person;
    private PassportDtoResponse passport;
    private String passportDeleteStatus;
    private List<CarDtoResponse> carList;
    private String carDeleteStatus;
    private List<HouseDtoResponse> houseLst;
    private String houseDeleteStatus;
}
