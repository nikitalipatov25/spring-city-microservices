package com.nikitalipatov.sauron.service.impl;

import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.sauron.converter.LogConverter;
import com.nikitalipatov.sauron.repository.CityLogRepository;
import com.nikitalipatov.sauron.repository.TimerLogRepository;
import com.nikitalipatov.sauron.service.CityLogService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class CityLogServiceImpl implements CityLogService {

    private final CityLogRepository cityLogRepository;
    private final LogConverter logConverter;
    private final TimerLogRepository timerLogRepository;

    @SneakyThrows
    @Override
    public void saveLogs(LogDto logs) {
        switch (logs.getLogType()) {
            case "UPDATE" -> updateLogs(logs);
            case "CREATE", "START_LOTTERY", "DELETE" ->  createLogs(logs);
        }
    }

    @SneakyThrows
    private void createLogs(LogDto logs) {
        cityLogRepository.save(logConverter.toEntity(logs));
    }

    @SneakyThrows
    private void updateLogs(LogDto logs) {
        var timerLog = timerLogRepository.findByLogEntity(logs.getLogEntity());
        if (timerLog.isEmpty()) {
            timerLogRepository.save(logConverter.toEntityTimer(logs));
        } else {
            var timerLogEntity = timerLog.get();
            timerLogRepository.save(logConverter.toEntity(timerLogEntity, logs));
        }
    }

    @Override
    @SneakyThrows
    public List<LogsResponse> getLogsByTime(String from, String to) {
        var logs = cityLogRepository.findAllByLogDateBetween(SIMPLE_DATE_FORMAT.parse(from), SIMPLE_DATE_FORMAT.parse(to));
        return logConverter.toLogsResponseDto(logs);
    }

    @Override
    public List<EntityLogsResponse> getActualLogs() {
        return logConverter.toDTO(timerLogRepository.findAll());
    }
}
