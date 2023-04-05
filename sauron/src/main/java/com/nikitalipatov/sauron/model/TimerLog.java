package com.nikitalipatov.sauron.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.nikitalipatov.common.constant.Constants.DATE_PATTERN;

@Entity
@Table(name ="timer_log")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TimerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String logEntity;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private Date logDate;
    private int numOfEntities;
}
