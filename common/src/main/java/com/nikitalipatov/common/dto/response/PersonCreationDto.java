package com.nikitalipatov.common.dto.response;

import com.nikitalipatov.common.dto.request.DeleteStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreationDto {
    private Enum<DeleteStatus> status;
    private int personId;
}
