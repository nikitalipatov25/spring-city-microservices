package com.nikitalipatov.passports.converter;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.passports.model.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PassportConverter {

    public PassportDtoResponse toDto(Passport passport) {
        return PassportDtoResponse.builder()
                .number(passport.getNumber())
                .serial(passport.getSerial())
                .build();
    }

    public List<PassportDtoResponse> toDto(List<Passport> passports) {
        List<PassportDtoResponse> passportDtoResponses = new ArrayList<>();
        for (Passport passport : passports) {
            passportDtoResponses.add(toDto(passport));
        }
        return passportDtoResponses;
    }

    public Passport toEntity(int personId) {
        return Passport.builder()
                .number((int) (Math.random() * (9999 - 1000) + 1000))
                .serial((int) (Math.random() * (9999 - 1000) + 1000))
                .ownerId(personId)
                .build();
    }
}
