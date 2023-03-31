package com.nikitalipatov.common.logs;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LogDto {
    private String logType;
    private String logEntity;
    private Date logDate;
    private int numOfEntities;
}
