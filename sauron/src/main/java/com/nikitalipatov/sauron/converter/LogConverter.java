package com.nikitalipatov.sauron.converter;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.sauron.model.Log;
import com.nikitalipatov.sauron.model.TimerLog;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.nikitalipatov.common.constant.Constants.SIMPLE_DATE_FORMAT;

@Component
@RequiredArgsConstructor
public class LogConverter {

    @SneakyThrows
    public Log toEntity(LogDto logs) {
        return Log.builder()
                .logEntity(logs.getLogEntity())
                .logType(logs.getLogType())
                .numOfEntities(logs.getNumOfEntities())
                .logDate(SIMPLE_DATE_FORMAT.parse(logs.getTime()))
                .build();
    }

    @SneakyThrows
    public TimerLog toEntity(TimerLog timerLog, LogDto logs) {
        return timerLog.toBuilder()
                .numOfEntities(logs.getNumOfEntities())
                .logDate(SIMPLE_DATE_FORMAT.parse(logs.getTime()))
                .build();
    }

    @SneakyThrows
    public TimerLog toEntityTimer(LogDto logs) {
        return TimerLog.builder()
                .logDate(SIMPLE_DATE_FORMAT.parse(logs.getTime()))
                .logEntity(logs.getLogEntity())
                .numOfEntities(logs.getNumOfEntities())
                .build();
    }

    public EntityLogsResponse toDTO(TimerLog timerLog) {
        return EntityLogsResponse.builder()
                .entityName(timerLog.getLogEntity())
                .numberOfEntities(timerLog.getNumOfEntities())
                .logDate(timerLog.getLogDate())
                .build();
    }

    public List<EntityLogsResponse> toDTO(List<TimerLog> timerLog) {
        var logs = new ArrayList<EntityLogsResponse>();
        timerLog.forEach(log -> logs.add(toDTO(log)));
        return logs;
    }

    public LogsResponse toLogsResponseDto(Log log) {
        return LogsResponse.builder()
                .logDate(log.getLogDate())
                .logType(log.getLogType())
                .logEntity(log.getLogEntity())
                .numOfEntities(log.getNumOfEntities())
                .build();
    }

    public List<LogsResponse> toLogsResponseDto(List<Log> logs) {
        var logsList = new ArrayList<LogsResponse>();
        logs.forEach(log -> logsList.add(toLogsResponseDto(log)));
        return logsList;
    }
}
