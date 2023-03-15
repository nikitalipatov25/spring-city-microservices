package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.enums.KafkaStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDeleteDto {
    private PersonDtoResponse person;
    private Enum<KafkaStatus> personDeleteStatus;
}
