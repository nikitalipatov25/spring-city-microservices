package com.nikitalipatov.sauron.repository;

import com.nikitalipatov.sauron.model.TimerLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimerLogRepository extends JpaRepository<TimerLog, Integer> {
    Optional<TimerLog> findByLogEntity(String name);
}
