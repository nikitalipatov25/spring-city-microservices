package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.kafka.KafkaObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDeleteDto extends KafkaObject {
    private PersonDtoResponse citizen;
    private Enum<KafkaStatus> citizenDeleteStatus;
    private PassportDtoResponse passport;
    private Enum<KafkaStatus> passportDeleteStatus;
    private List<CarDtoResponse> carList;
    private Enum<KafkaStatus> carDeleteStatus;
    private List<HouseDtoResponse> houseLst;
    private Enum<KafkaStatus> houseDeleteStatus;
}
