package ru.bironix.super_food.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.zalando.logbook.Logbook;

import static com.google.common.collect.Sets.newHashSet;
import static org.zalando.logbook.Conditions.*;

@Configuration
public class Log {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        contentType("application/octet-stream"),
                        header("X-Secret", newHashSet("1", "true")::contains)))
                .build();
        return logbook;
    }

}
