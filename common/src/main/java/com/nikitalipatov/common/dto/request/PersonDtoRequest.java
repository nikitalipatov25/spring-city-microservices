package com.nikitalipatov.common.dto.request;

import com.nikitalipatov.common.enums.ModelStatus;
import lombok.*;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoRequest {

    @Builder.Default
    private String fullName = "Citizen Clone" + ThreadLocalRandom.current().nextInt(1, 9999);
    @Builder.Default
    private int age = ThreadLocalRandom.current().nextInt(18, 99);
    @Builder.Default
    private String sex = "Clone";
    @Builder.Default
    private String status = ModelStatus.ACTIVE.name();
}