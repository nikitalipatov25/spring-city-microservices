package com.nikitalipatov.houses.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "house_person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class HousePerson {

    // todo Это у тебя уже не таблица посредник, первая проблема, например тут могут быть дубликаты связки houseId и personId,
    //  тут лучше использовать составной ключ, Поресерчи в сторону @EmbeddedId

    @Id
    @SequenceGenerator(name = "house_person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_person_seq")
    private int recordId;
    private int houseId;
    private int personId;
    @ManyToOne()
    @JoinColumn(name = "h_id")
    private House house;
}
