package com.nikitalipatov.sauron.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name ="city_log")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Log {

    @Id
    @SequenceGenerator(name = "log_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    private int id;
    private String logEntity;
    private String logType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;
    private int numOfEntities;
}
