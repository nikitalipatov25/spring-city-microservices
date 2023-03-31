package com.nikitalipatov.common.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoResponse {
    private String name;
    private int age;
    private String sex;
}
