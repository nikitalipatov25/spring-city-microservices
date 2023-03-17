package com.nikitalipatov.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenEvent extends Object{
    private int citizenId;
}
