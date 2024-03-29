package com.nikitalipatov.passports.repository;

import com.nikitalipatov.passports.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Integer> {
    void deleteByOwnerId(int personId);

    List<Passport> findAllByStatusAndOwnerIdIn(String status, List<Integer> ownerIds);

    @Query(value = "select count(*) from Passport p WHERE p.status = 'ACTIVE'")
    int countActivePassports();
}
