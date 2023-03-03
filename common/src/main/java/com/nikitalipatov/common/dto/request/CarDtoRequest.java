package com.nikitalipatov.common.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoRequest {

    private int ownerId;
    private String gosNumber;
    private String model;
    private String name;
    private String type;
    private String color;
}
