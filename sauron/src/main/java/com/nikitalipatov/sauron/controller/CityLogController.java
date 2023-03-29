package com.nikitalipatov.sauron.controller;

import com.nikitalipatov.common.logs.LogsResponse;
import com.nikitalipatov.common.logs.LogDto;
import com.nikitalipatov.common.logs.EntityLogsResponse;
import com.nikitalipatov.sauron.service.CityLogService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    public void saveLogs(LogDto logDto) {
       cityLogService.saveLogs(logDto);
    }

    @GetMapping("/{from}/{to}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @SneakyThrows
    public List<LogsResponse> getLogsByTime(@PathVariable String from, @PathVariable String to) {
       return cityLogService.getLogsByTime(from, to);
    }

    @GetMapping("/actual")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<EntityLogsResponse> getLogsForEntity() {
        return cityLogService.getActualLogs();
    }
}
