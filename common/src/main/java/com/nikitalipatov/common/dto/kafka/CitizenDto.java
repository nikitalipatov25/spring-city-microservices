package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.kafka.KafkaObject;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto extends KafkaObject {
    private int citizenId;
}
