package com.nikitalipatov.passports.repository;

import com.nikitalipatov.passports.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Integer> {
}
