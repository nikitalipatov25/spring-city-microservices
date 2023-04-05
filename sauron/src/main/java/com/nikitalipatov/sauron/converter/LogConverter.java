package com.nikitalipatov.sauron.converter;

import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.sauron.mapper.OCMapper;
import com.nikitalipatov.sauron.model.Log;
import com.nikitalipatov.sauron.model.TimerLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LogConverter {

    private final OCMapper OCMapper;

    public Log toEntity(LogDto logs) {
        return OCMapper.toLog(logs);
    }

    public TimerLog toEntity(TimerLog timerLog, LogDto logs) {
        return OCMapper.updateTimerLog(logs, timerLog);
    }

    public TimerLog toEntityTimer(LogDto logs) {
        return OCMapper.toTimerLog(logs);
    }

    public EntityLogsResponse toDTO(TimerLog timerLog) {
        return OCMapper.toEntityLogsResponse(timerLog);
    }

    public LogsResponse toLogsResponseDto(Log log) {
        return OCMapper.toLogsResponse(log);
    }

    public List<EntityLogsResponse> toDTO(List<TimerLog> timerLog) {
        var logs = new ArrayList<EntityLogsResponse>();
        timerLog.forEach(log -> logs.add(toDTO(log)));
        return logs;
    }

    public List<LogsResponse> toLogsResponseDto(List<Log> logs) {
        var logsList = new ArrayList<LogsResponse>();
        logs.forEach(log -> logsList.add(toLogsResponseDto(log)));
        return logsList;
    }
}
