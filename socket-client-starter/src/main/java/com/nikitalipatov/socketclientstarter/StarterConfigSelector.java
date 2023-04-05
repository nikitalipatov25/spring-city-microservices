package com.nikitalipatov.socketclientstarter;

import com.nikitalipatov.socketclientstarter.config.annotation.EnableSauronWs;
import com.nikitalipatov.socketclientstarter.error.StarterLoadException;
import lombok.SneakyThrows;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class StarterConfigSelector implements ImportSelector {

    @SneakyThrows
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableSauronWs.class.getName(), false));
        boolean criteria = attributes.getBoolean("criteria");
        if (criteria) {
            return new String[] {"com.nikitalipatov.socketclientstarter.config.StarterAutoConfiguration"};
        } else {
            throw new StarterLoadException("Define @EnableSauronWS annotation with criteria = true param");
        }
    }
}
