package com.nikitalipatov.sauron.service.impl;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.MyLog;
import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.sauron.converter.LogConverter;
import com.nikitalipatov.sauron.model.Log;
import com.nikitalipatov.sauron.repository.CityLogRepository;
import com.nikitalipatov.sauron.repository.TimerLogRepository;
import com.nikitalipatov.sauron.service.CityLogService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CityLogServiceImpl implements CityLogService {

    private final CityLogRepository cityLogRepository;
    private final LogConverter logConverter;
    private final TimerLogRepository timerLogRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @SneakyThrows
    @Override
    public void saveLogs(MyLog logs) {
        switch (logs.getLogType()) {
            case "update" -> updateLogs(logs);
            case "create" ->  createLogs(logs);
        }
    }

    @SneakyThrows
    private void createLogs(MyLog logs) {
        cityLogRepository.save(logConverter.toEntity(logs));
    }

    @SneakyThrows
    private void updateLogs(MyLog logs) {
        var timerLog = timerLogRepository.findByLogEntity(logs.getLogEntity());
        if (timerLog.isEmpty()) {
            timerLogRepository.save(logConverter.toEntityTimer(logs));
        } else {
            var timerLogEntity = timerLog.get();
            timerLogRepository.save(logConverter.toEntity(timerLogEntity, logs));
        }
    }

    @Override
    public List<LogsResponse> getLogsByTime(String from, String to) throws ParseException {
        var logs = cityLogRepository.findAllByLogTimeBetween(formatter.parse(from), formatter.parse(to));
        return logConverter.toDTOLogs(logs);
    }

    @Override
    public List<EntityLogsResponse> getActualLogs() {
        return logConverter.toDTO(timerLogRepository.findAll());
    }
}
