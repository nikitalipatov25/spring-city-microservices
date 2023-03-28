package com.nikitalipatov.sauron.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name ="timer_log")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TimerLog {
    @Id
    @SequenceGenerator(name = "timer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timer_seq")
    private int id;
    private String logEntity;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;
    private int numOfEntities;
}
