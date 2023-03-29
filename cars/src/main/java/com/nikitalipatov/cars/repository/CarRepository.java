package com.nikitalipatov.cars.repository;

import com.nikitalipatov.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    void deleteAllByIdIn(List<Integer> ids);
    void deleteAllByOwnerId(int personId);
    List<Car> findAllByOwnerId(int personId);
    //    именованные запросы?
    @Query(value = "SELECT c.id, c.ownerId, c.status, c.color, c.gosNumber, c.model, c.name, c.price FROM Car c WHERE c.status = 'ACTIVE'")
    List<Car> findAllActive();
    @Query("SELECT count(*) FROM Car c WHERE c.status = 'ACTIVE'")
    int countActiveCars();
}
