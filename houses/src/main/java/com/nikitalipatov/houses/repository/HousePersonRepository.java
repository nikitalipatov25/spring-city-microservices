package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HousePersonRepository extends JpaRepository<HousePerson, HousePersonId> {
    void deleteAllByHousePersonId(HousePersonId housePersonId);
}
