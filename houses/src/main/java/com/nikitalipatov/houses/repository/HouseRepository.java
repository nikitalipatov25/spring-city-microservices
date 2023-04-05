package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Integer> {

    @Query(value = "SELECT house.id, house.street, house.number FROM house JOIN house_person ON house.id = house_person.house_id WHERE house_person.owner_id = ?1", nativeQuery = true)
    List<House> getHousesByOwnerId(int personId);

    @Query(value = "SELECT h.id, h.street, h.number, h.status FROM House h WHERE h.status = 'ACTIVE'")
    List<House> findAllActive();

    @Query(value = "select count(*) from House h WHERE h.status = 'ACTIVE'")
    int countActiveHouses();
}
