package com.nikitalipatov.socketclientstarter.config.annotation;

import com.nikitalipatov.socketclientstarter.StarterConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(StarterConfigSelector.class)
public @interface EnableSauronWs {
    boolean criteria() default false;
}
