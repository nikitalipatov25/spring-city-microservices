package com.nikitalipatov.passports.repository;

import com.nikitalipatov.passports.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Integer> {
    void deleteByOwnerId(int personId);
}
