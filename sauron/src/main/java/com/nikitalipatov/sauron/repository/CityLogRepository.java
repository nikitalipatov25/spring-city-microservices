package com.nikitalipatov.sauron.repository;

import com.nikitalipatov.sauron.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CityLogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByLogDateBetween(Date date1, Date date2);
}
