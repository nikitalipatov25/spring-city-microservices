package com.nikitalipatov.sauron.controller;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.MyLog;
import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.sauron.service.CityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/logs")
public class CityLogController {

    private final CityLogService cityLogService;

    @MessageMapping("/logs")
    @SendTo("/topic/logs")
    public void saveLogs(MyLog myLog) {
       cityLogService.saveLogs(myLog);
    }

    @GetMapping("/{from}/{to}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<LogsResponse> getLogsByTime(@PathVariable String from, @PathVariable String to) throws ParseException {
       return cityLogService.getLogsByTime(from, to);
    }

    @GetMapping("/actual")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<EntityLogsResponse> getLogsForEntity() {
        return cityLogService.getActualLogs();
    }
}
