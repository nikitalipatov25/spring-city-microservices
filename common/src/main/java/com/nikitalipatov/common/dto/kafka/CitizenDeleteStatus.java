package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.dto.response.PersonDtoResponse;
import com.nikitalipatov.common.enums.KafkaStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDeleteStatus {
    private PersonDtoResponse person;
    private Enum<KafkaStatus> personDeleteStatus;
}
