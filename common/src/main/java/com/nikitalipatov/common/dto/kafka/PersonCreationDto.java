package com.nikitalipatov.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreationDto {
    private String status;
    private int personId;
}
