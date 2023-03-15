package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.kafka.KafkaObject;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportDeleteStatus extends KafkaObject {
    private PassportDtoResponse passport;
    private Enum<KafkaStatus> passportDeleteStatus;
}
