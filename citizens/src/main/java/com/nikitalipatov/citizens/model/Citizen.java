package com.nikitalipatov.citizens.model;

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
    @SequenceGenerator(name = "person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    private int id;

    private String fullName;

    private int age;

    private String sex;

}
