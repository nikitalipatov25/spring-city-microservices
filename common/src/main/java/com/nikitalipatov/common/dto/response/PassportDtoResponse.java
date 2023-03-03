package com.nikitalipatov.common.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportDtoResponse {

    private int number;
    private int serial;
}
