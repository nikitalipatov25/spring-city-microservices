package com.nikitalipatov.common.logs;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityLogsResponse {

    private String entityName;
    private Date time;
    private int numberOfEntities;
}
