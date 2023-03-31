package com.nikitalipatov.sauron.mapper;

import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.sauron.model.Log;
import com.nikitalipatov.sauron.model.TimerLog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OCMapper {
    Log toLog(LogDto logDto);
    TimerLog toTimerLog(LogDto logDto);
    TimerLog updateTimerLog(LogDto logDto, @MappingTarget TimerLog timerLog);
    EntityLogsResponse toEntityLogsResponse(TimerLog timerLog);
    LogsResponse toLogsResponse(Log log);
}
