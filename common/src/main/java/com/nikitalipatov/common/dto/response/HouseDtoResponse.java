package com.nikitalipatov.common.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDtoResponse {

    private String street;
    private String number;
}
