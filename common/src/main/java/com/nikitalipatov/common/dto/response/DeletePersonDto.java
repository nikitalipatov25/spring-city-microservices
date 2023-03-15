package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.request.KafkaStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletePersonDto {
    private PersonDtoResponse person;
    private Enum<KafkaStatus> personDeleteStatus;
    private PassportDtoResponse passport;
    private Enum<KafkaStatus> passportDeleteStatus;
    private List<CarDtoResponse> carList;
    private Enum<KafkaStatus> carDeleteStatus;
    private List<HouseDtoResponse> houseLst;
    private Enum<KafkaStatus> houseDeleteStatus;
}
