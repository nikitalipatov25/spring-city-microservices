package com.nikitalipatov.citizens.kafka;

import com.nikitalipatov.common.dto.response.DeletePersonDto;
import com.nikitalipatov.common.dto.response.PersonCreationDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaPersonConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:29092");
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "test-gi");
        return new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

//    @Bean
//    public RecordMessageConverter multiTypeConverter() {
//        StringJsonMessageConverter converter = new StringJsonMessageConverter();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//        typeMapper.addTrustedPackages("com.nikitalipatov.common.dto.response");
//        Map<String, Class<?>> mappings = new HashMap<>();
//        //mappings.put("personCreationDto", PersonCreationDto.class);
//        mappings.put("DeletePersonDto", DeletePersonDto.class);
//        typeMapper.setIdClassMapping(mappings);
//        converter.setTypeMapper(typeMapper);
//        return converter;
//    }

//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//        jsonDeserializer.addTrustedPackages("*");
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:29092");
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                "test-gi");
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                jsonDeserializer); //!
//        //props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//
//        return new DefaultKafkaConsumerFactory<>(
//                props,
//                new StringDeserializer(),
//                jsonDeserializer);
//    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaPersonListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }


//    @Bean
//    public ConsumerFactory<String, PersonCreationDto> consumerFactoryCreate() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:29092");
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                "test-gi");
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JsonDeserializer.class); //!
//        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//
//        return new DefaultKafkaConsumerFactory<>(
//                props,
//                new StringDeserializer(),
//                new JsonDeserializer<>(PersonCreationDto.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, PersonCreationDto>
//    kafkaPersonListenerContainerFactoryCreation() {
//
//        ConcurrentKafkaListenerContainerFactory<String, PersonCreationDto> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryCreate());
//        return factory;
//    }
}
