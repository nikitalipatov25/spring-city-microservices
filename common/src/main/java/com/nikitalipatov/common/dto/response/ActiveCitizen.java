package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.enums.WorkPlace;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveCitizen {
    private int id;
    private String fullName;
    @Builder.Default
    private String work = WorkPlace.FACTORY.name();
}
