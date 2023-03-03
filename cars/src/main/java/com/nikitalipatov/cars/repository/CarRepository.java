package com.nikitalipatov.cars.repository;

import com.nikitalipatov.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    void deleteAllByIdIn(List<Integer> ids);
    void deleteAllByOwnerId(int personId);
    List<Car> findAllByOwnerId(int personId);
}
