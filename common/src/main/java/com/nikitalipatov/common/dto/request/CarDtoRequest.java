package com.nikitalipatov.common.dto.request;

import com.nikitalipatov.common.enums.ModelStatus;
import lombok.*;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CarDtoRequest {

    private int ownerId;
    @Builder.Default
    private String gosNumber = "LOTTERY";
    @Builder.Default
    private String model = "BMW";
    @Builder.Default
    private String name = "M5";
    @Builder.Default
    private String color = "Red";
    @Builder.Default
    private ModelStatus status = ModelStatus.ACTIVE;
}
