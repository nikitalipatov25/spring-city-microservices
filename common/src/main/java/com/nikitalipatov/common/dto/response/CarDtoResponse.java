package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.enums.ModelStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoResponse {

    private String gosNumber;
    private String model;
    private String name;
    private String color;
    private int ownerId;
    private String status;
}
