package com.nikitalipatov.citizens.model;


import com.nikitalipatov.cars.model.Car;
import com.nikitalipatov.houses.model.House;
import com.nikitalipatov.passports.model.Passport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name ="person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Citizen {

    @Id
    @SequenceGenerator(name = "person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    private int id;

    private String fullName;

    private int age;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Passport passport;

    @ManyToMany()
    @JoinTable(name = "house_person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private Set<House> house;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Car> car;

    public Citizen(String fullName, int age, Passport passport) {
        this.fullName = fullName;
        this.age = age;
        this.passport = passport;
    }
}
