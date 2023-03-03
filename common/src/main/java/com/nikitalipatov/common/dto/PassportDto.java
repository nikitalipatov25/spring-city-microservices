package com.nikitalipatov.common.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportDto {

    private int number;
    private int serial;
}
