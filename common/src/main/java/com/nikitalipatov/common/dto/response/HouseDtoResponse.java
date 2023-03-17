package com.nikitalipatov.common.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDtoResponse {

    private String city;
    private String street;
    private String number;
    private int houseId;
    private List<Integer> citizenIds;
}
