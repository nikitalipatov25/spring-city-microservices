package com.nikitalipatov.citizens.repository;

import com.nikitalipatov.citizens.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

    Citizen findByFullName(String name);

    @Query(value = "SELECT * FROM person WHERE person.status = 'ACTIVE'", nativeQuery = true)
    List<Citizen> findAllActive();

    @Query("SELECT count(*) FROM Citizen c WHERE c.status = 'ACTIVE'")
    int countActiveCitizens();

}
