package com.nikitalipatov.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonCarDto {
    private String name;
    private List<CarDto> carList;
}