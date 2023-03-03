package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.response.HouseDtoResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonHouseDtoResponse {
    private String name;
    private List<HouseDtoResponse> houses;
}
