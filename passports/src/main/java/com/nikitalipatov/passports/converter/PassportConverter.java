package com.nikitalipatov.passports.converter;

import com.nikitalipatov.common.dto.PassportDto;
import com.nikitalipatov.passports.model.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassportConverter {

    public PassportDto toDto(Passport passport) {
        return PassportDto.builder()
                .number(passport.getNumber())
                .serial(passport.getSerial())
                .build();
    }

    public Passport toEntity(int personId) {
        return Passport.builder()
                .number((int) (Math.random() * (9999 - 1000) + 1000))
                .serial((int) (Math.random() * (9999 - 1000) + 1000))
                .ownerId(personId)
                .build();
    }
}
