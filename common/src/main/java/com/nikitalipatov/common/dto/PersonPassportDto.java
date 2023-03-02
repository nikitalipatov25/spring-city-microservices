package com.nikitalipatov.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonPassportDto {
    private String name;
    private int age;
    private String sex;
    private int passportNumber;
    private int passportSerial;
}

