package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import com.nikitalipatov.common.enums.KafkaStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDeleteStatus {
    private List<HouseDtoResponse> houseLst;
    private Enum<KafkaStatus> houseDeleteStatus;
}
