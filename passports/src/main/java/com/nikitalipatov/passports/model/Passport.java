package com.nikitalipatov.passports.model;

import com.nikitalipatov.common.enums.ModelStatus;
import jakarta.persistence.*;
import lombok.*;

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
    private String status;

}
