package com.nikitalipatov.common.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TDto {

    int personId;
    String message;
}
