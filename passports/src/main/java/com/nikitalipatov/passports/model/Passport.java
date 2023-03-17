package com.nikitalipatov.passports.model;

import com.nikitalipatov.common.enums.ModelStatus;
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
    private ModelStatus status;

}
