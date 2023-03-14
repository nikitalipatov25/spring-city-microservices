package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.request.DeleteStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDeleteDto {
    private PersonDtoResponse person;
    private Enum<DeleteStatus> personDeleteStatus;
}
