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
public class Car {

    @Id
    @SequenceGenerator(name = "car_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
    private int id;

    private java.lang.String gosNumber;

    private java.lang.String model;

    private java.lang.String name;

    private java.lang.String color;

    private double price;

    private int ownerId;

    private String status;
}
