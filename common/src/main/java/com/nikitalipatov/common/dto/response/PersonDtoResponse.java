package com.nikitalipatov.common.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoResponse {
    private int personId;
    private String name;
    private int age;
    private String sex;
    private int passportSerial;
    private int passportNumber;
}
