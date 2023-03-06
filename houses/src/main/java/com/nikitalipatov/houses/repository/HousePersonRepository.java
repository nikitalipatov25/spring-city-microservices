package com.nikitalipatov.houses.repository;

import com.nikitalipatov.houses.key.HousePersonId;
import com.nikitalipatov.houses.model.HousePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HousePersonRepository extends JpaRepository<HousePerson, HousePersonId> {
    @Query(value = "DELETE FROM house_person WHERE house_person.person_id =?1", nativeQuery = true)
    void deleteAllByHousePersonId(int personId);
}
