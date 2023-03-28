package com.nikitalipatov.common.logs;

import lombok.*;

import java.text.SimpleDateFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MyLog {

    private String logType;
    private String logEntity;
    private String time;
    private int numOfEntities;
}
