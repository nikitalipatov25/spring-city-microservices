package com.nikitalipatov.common.dto.request;

import com.nikitalipatov.common.enums.ModelStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoRequest {

    private int ownerId;
    private java.lang.String gosNumber;
    private java.lang.String model;
    private java.lang.String name;
    private java.lang.String type;
    private java.lang.String color;
    private ModelStatus status;
}
