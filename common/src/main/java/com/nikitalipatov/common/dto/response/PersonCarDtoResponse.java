package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.response.CarDtoResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonCarDtoResponse {
    private String name;
    private List<CarDtoResponse> carList;
}