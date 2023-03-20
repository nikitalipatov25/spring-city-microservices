package com.nikitalipatov.citizens.repository;

import com.nikitalipatov.citizens.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

    Citizen findByFullName(String name);

    @Query(value = "SELECT person.id, person.full_name, person.age, person.passport_id FROM person JOIN FETCH house_person ON person.id = house_person.person_id WHERE house_person.house_id = ?1" , nativeQuery = true)
    List<Citizen> findAllByHouseIn(int id);

    @Query(value = "SELECT person.id, person.full_name, person.age, person.passport_id, passport.number, passport.serial, passport.sex FROM person JOIN passport on person.id = passport.id WHERE person.full_name LIKE ?1%", nativeQuery = true)
    List<Citizen> findPassportDataByFullNameLike(String text);

    @Query(value = "SELECT DISTINCT person.id, person.full_name, person.age, person.passport_id FROM house_person JOIN house ON house.id = house_person.house_id JOIN person ON person.id = house_person.person_id WHERE house.street =?1", nativeQuery = true)
    List<Citizen> findAllByStreet(String street);

    @Query(value = "SELECT DISTINCT person.id, person.full_name, person.age, person.passport_id FROM person JOIN car ON person.id = car.person_id WHERE person.full_name = ?1", nativeQuery = true)
    List<Citizen> findCarsByFullName(String name);

    @Query(value = "SELECT DISTINCT person.id, person.full_name, person.age, person.passport_id FROM person JOIN house_person ON person.id = house_person.person_id WHERE person.full_name =?1", nativeQuery = true)
    List<Citizen> findHousesByFullName(String name);

    @Query(value = "SELECT c.id, c.fullName, c.age, c.sex, c.status FROM Citizen c WHERE c.status = 'ACTIVE'")
    List<Citizen> findAllActive();

}
