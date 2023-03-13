package com.nikitalipatov.houses.kafka;

import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.common.feign.HouseClient;
import com.nikitalipatov.common.feign.PassportClient;
import com.nikitalipatov.houses.service.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "houseEvents", groupId = "test-gi", containerFactory = "kafkaHouseListenerContainerFactory")
public class HouseListener {

//    private final HouseService houseService;
//    private final PassportClient passportClient;
//    private final CarClient carClient;
//    private final KafkaTemplate<String, DeletePersonDto> kafkaTemplate;

    @KafkaHandler
    public DeletePersonDto handleHouseDelete(DeletePersonDto deletePersonDto) {
        return DeletePersonDto.builder()
                .houseDeleteStatus(deletePersonDto.getHouseDeleteStatus())
                .houseLst(deletePersonDto.getHouseLst())
                .build();
    }
}
