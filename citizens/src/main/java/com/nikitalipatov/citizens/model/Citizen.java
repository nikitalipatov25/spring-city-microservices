package com.nikitalipatov.citizens.model;

import com.nikitalipatov.common.enums.ModelStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String fullName;

    private int age;

    private String sex;

    private String status;
}
