package com.nikitalipatov.sauron.service;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.MyLog;
import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.sauron.model.Log;

import java.text.ParseException;
import java.util.List;

public interface CityLogService {
    void saveLogs(MyLog logs);
    List<EntityLogsResponse> getActualLogs();
    List<LogsResponse> getLogsByTime(String date1, String date2) throws ParseException;
}
