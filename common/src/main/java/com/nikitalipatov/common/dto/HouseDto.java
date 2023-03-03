package com.nikitalipatov.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto {

    private String city;
    private String street;
    private String number;
    private List<Integer> citizenIds;
}
