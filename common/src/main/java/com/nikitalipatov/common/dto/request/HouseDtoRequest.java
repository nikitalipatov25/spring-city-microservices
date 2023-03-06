package com.nikitalipatov.common.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDtoRequest {

    private String street;
    private String number;
}
