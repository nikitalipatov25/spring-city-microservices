package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.request.KafkaStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreationDto {
    private Enum<KafkaStatus> status;
    private int personId;
}
