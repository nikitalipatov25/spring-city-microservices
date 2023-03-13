package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HousePersonRepository extends JpaRepository<HousePerson, HousePersonId> {

    @Query(value = "DELETE FROM HousePerson AS hp WHERE hp.housePersonId.ownerId =?1")
    void deleteByOwnerId(int ownerId);

}
