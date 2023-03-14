package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.request.DeleteStatus;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletePersonDto {
    private PersonDtoResponse person;
    private Enum<DeleteStatus> personDeleteStatus;
    private PassportDtoResponse passport;
    private Enum<DeleteStatus> passportDeleteStatus;
    private List<CarDtoResponse> carList;
    private Enum<DeleteStatus> carDeleteStatus;
    private List<HouseDtoResponse> houseLst;
    private Enum<DeleteStatus> houseDeleteStatus;
}
