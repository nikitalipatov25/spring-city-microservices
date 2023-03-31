package com.nikitalipatov.common.mapper;

import com.nikitalipatov.common.logs.LogDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper()
public interface LogMapper {
    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);
    LogDto toLogDto(String logType, String logEntity, Date logDate, int numOfEntities);
}
