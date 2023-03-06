package com.nikitalipatov.passports.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "passport")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Passport {
    @Id
    private int ownerId;
    private int serial;
    private int number;

}
