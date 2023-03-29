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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String logEntity;
    private String logType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logDate;
    private int numOfEntities;
}
