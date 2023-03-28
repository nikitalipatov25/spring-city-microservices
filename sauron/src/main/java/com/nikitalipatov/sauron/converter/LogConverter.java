package com.nikitalipatov.sauron.converter;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.MyLog;
import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.sauron.model.Log;
import com.nikitalipatov.sauron.model.TimerLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LogConverter {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public Log toEntity(MyLog logs) throws ParseException {
        return Log.builder()
                .logEntity(logs.getLogEntity())
                .logType(logs.getLogType())
                .numOfEntities(logs.getNumOfEntities())
                .logTime(formatter.parse(logs.getTime()))
                .build();
    }
    public TimerLog toEntity(TimerLog timerLog, MyLog logs) throws ParseException {
        return timerLog.toBuilder()
                .numOfEntities(logs.getNumOfEntities())
                .logTime(formatter.parse(logs.getTime()))
                .build();
    }

    public TimerLog toEntityTimer(MyLog logs) throws ParseException {
        return TimerLog.builder()
                .logTime(formatter.parse(logs.getTime()))
                .logEntity(logs.getLogEntity())
                .numOfEntities(logs.getNumOfEntities())
                .build();
    }

    public EntityLogsResponse toDTO(TimerLog timerLog) {
        return EntityLogsResponse.builder()
                .entityName(timerLog.getLogEntity())
                .numberOfEntities(timerLog.getNumOfEntities())
                .time(timerLog.getLogTime())
                .build();
    }

    public List<EntityLogsResponse> toDTO(List<TimerLog> timerLog) {
        var logs = new ArrayList<EntityLogsResponse>();
        timerLog.forEach(log -> logs.add(toDTO(log)));
        return logs;
    }

    public LogsResponse toDTOLog(Log log) {
        return LogsResponse.builder()
                .logDate(log.getLogTime())
                .logType(log.getLogType())
                .logEntity(log.getLogEntity())
                .numOfEntities(log.getNumOfEntities())
                .build();
    }

    public List<LogsResponse> toDTOLogs(List<Log> logs) {
        var logsList = new ArrayList<LogsResponse>();
        logs.forEach(log -> logsList.add(toDTOLog(log)));
        return logsList;
    }
}
