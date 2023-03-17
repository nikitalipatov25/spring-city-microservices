package com.nikitalipatov.common.dto.kafka;

import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage<T> {
    private UUID messageId;
    private Status status;
    private EventType eventType;
    private T payload;
}
