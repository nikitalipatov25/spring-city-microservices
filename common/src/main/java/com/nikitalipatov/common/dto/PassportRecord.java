package com.nikitalipatov.common.dto;

import java.util.Date;

public record PassportRecord(int serial, int number, String address, String addressFact, String placeOfBirth, Date dateOfBirth, String sex,
                             Date issued, String issuedBy) {
}
