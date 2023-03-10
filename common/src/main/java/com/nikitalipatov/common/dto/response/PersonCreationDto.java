package com.nikitalipatov.common.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreationDto {
    private String status;
    private int personId;
}
