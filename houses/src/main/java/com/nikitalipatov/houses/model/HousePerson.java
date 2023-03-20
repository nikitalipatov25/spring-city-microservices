package com.nikitalipatov.houses.model;

import com.nikitalipatov.common.enums.ModelStatus;
import com.nikitalipatov.houses.key.HousePersonId;
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

    @EmbeddedId
    private HousePersonId housePersonId;

    private String status;
}

