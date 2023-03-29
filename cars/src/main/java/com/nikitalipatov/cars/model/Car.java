package com.nikitalipatov.cars.model;

import com.nikitalipatov.common.enums.ModelStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String gosNumber;

    private String model;

    private String name;

    private String color;

    private int ownerId;

    private String status;

}
