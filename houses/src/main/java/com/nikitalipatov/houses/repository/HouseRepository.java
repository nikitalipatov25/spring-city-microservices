package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Integer> {
}
