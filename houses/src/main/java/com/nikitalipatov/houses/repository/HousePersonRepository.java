package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HousePersonRepository extends JpaRepository<HousePerson, HousePersonId> {
    @Query(value = "DELETE FROM house_person WHERE house_person.owner_id = ?1", nativeQuery = true)
    void deleteAllByHousePersonId(int ownerId);

//    @Query(value = "GET * FROM house_person JOIN house ON house_person.house_id = house.id WHERE house_person.owner_id = ?1", nativeQuery = true)
//    List<HousePerson> getAllHousesByOwnerId(int ownerId);

}
