package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.enums.KafkaStatus;
import com.nikitalipatov.common.kafka.KafkaObject;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassportCreateDto extends KafkaObject {
    private Enum<KafkaStatus> passportCreateStatus;
    private int ownerId;
}
