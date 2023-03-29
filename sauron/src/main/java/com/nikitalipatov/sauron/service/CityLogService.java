package com.nikitalipatov.sauron.service;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.logs.EntityLogsResponse;

import java.text.ParseException;
import java.util.List;

public interface CityLogService {
    void saveLogs(LogDto logs);
    List<EntityLogsResponse> getActualLogs();
    List<LogsResponse> getLogsByTime(String date1, String date2);
}
