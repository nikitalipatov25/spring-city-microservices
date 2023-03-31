package com.nikitalipatov.common.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitizenWithPassportDto {
    private String name;
    private int age;
    private String sex;
    private int passportNumber;
    private int passportSerial;
}

