package com.nikitalipatov.common.dto.response;

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
    private String type;
    private String color;
    private double price;
}
