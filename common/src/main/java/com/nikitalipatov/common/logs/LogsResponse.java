package com.nikitalipatov.common.logs;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LogsResponse {
    private Date logDate;
    private String logEntity;
    private String logType;
    private int numOfEntities;
}
