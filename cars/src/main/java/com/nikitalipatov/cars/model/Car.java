package com.nikitalipatov.cars.model;

import com.nikitalipatov.citizens.model.Citizen;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    private String gosNumber;

    private String model;

    private String name;

    private String type;

    private String color;

    private double price;

    @ManyToOne()
    @JoinColumn(name = "person_id")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Citizen person;

//    @ManyToOne()
//    @JoinColumn(name = "shop_id")
//    @Fetch(FetchMode.SELECT)
//    @BatchSize(size = 10)
//    private CarShop carShop;

    public Car(String gosNumber, String model, String name, String type, String color) {
        this.gosNumber = gosNumber;
        this.model = model;
        this.name = name;
        this.type = type;
        this.color = color;
    }

    public Car(String gosNumber, String model, String name, String type, String color, double price) {
        this.gosNumber = gosNumber;
        this.model = model;
        this.name = name;
        this.type = type;
        this.color = color;
        this.price = price;
    }
}
