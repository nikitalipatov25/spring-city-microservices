package com.nikitalipatov.houses.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "house")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class House {

    @Id
    @SequenceGenerator(name = "house_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_seq")
    private int id;
    private String city;
    private String street;
    private String number;

    public House(String city, String street, String number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }
}