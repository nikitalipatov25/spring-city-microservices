package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.dto.response.CarDtoResponse;
import com.nikitalipatov.common.enums.KafkaStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDeleteStatus {
    private List<CarDtoResponse> carList;
    private Enum<KafkaStatus> carDeleteStatus;
}
