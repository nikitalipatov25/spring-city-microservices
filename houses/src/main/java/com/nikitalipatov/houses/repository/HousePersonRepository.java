package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HousePersonRepository extends JpaRepository<HousePerson, Integer> {
    void deleteAllByPersonId(int personId);
}
