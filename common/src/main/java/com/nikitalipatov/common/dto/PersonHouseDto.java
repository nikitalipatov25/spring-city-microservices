package com.nikitalipatov.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonHouseDto {
    private String name;
    private List<HouseDto> houses;
}
