package com.nikitalipatov.passports.service;

import com.nikitalipatov.common.dto.PassportRecord;
import com.nikitalipatov.passports.model.Passport;

public interface PassportService {

    Passport create(PassportRecord passportRecord);

    void delete(int id);

}
