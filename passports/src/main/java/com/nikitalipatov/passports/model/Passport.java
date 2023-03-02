package com.nikitalipatov.passports.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "passport")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Passport {
    @Id
    @SequenceGenerator(name = "passport_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport_seq")
    private int id;
    private int serial;
    private int number;
    private String address;
    private String addressFact;
    private String placeOfBirth;
    private Date dateOfBirth;
    private String sex;
    private Date issued;
    private String issuedBy;

    public Passport(int serial, int number, String address, String addressFact, String placeOfBirth, Date dateOfBirth, String sex, Date issued, String issuedBy) {
        this.serial = serial;
        this.number = number;
        this.address = address;
        this.addressFact = addressFact;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.issued = issued;
        this.issuedBy = issuedBy;
    }
}
