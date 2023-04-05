package com.nikitalipatov.passports.mapper;

import com.nikitalipatov.common.dto.response.PassportDtoResponse;
import com.nikitalipatov.passports.model.Passport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    PassportDtoResponse passportToPassportDtoResponse(Passport passport);
}
