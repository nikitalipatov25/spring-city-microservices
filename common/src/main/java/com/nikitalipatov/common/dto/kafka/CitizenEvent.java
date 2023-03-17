package com.nikitalipatov.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenEvent {
    private int citizenId;
}
