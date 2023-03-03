package com.nikitalipatov.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private String name;
    private int age;
    private String sex;
    private int passportSerial;
    private int passportNumber;
//    private List<CarDto> car;
//    private List<HouseDto> house;
    //private List<BankDto> bank;
}
