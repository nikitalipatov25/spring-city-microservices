package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HousePersonRepository extends JpaRepository<HousePerson, HousePersonId> {

    @Query(value = "SELECT hp.housePersonId, hp.status FROM HousePerson hp WHERE hp.housePersonId.ownerId=:ownerId")
    List<HousePerson> getCitizenHouses(@Param("ownerId") int ownerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM HousePerson hp WHERE hp.housePersonId.ownerId=:ownerId")
    void deleteByOwnerId(@Param("ownerId") int ownerId);
}
