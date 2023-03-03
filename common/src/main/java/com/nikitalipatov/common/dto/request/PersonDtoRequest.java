package com.nikitalipatov.common.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoRequest {

    String fullName;
    int age;
    String sex;
}