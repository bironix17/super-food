package ru.bironix.super_food.config.log;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultHttpLogWriter;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;

import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;

@Configuration
public class LoggingRequestResponse {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
                .condition(exclude(
                        requestTo("/view/**"),
                        requestTo("/styles/**"),

                        requestTo("/swagger-ui/**"),
                        requestTo("/swagger-resources/**"),
                        requestTo("/v3/api-docs/**")
                ))
                .sink(new DefaultSink(
                        new CustomHttpLogFormatter(), new DefaultHttpLogWriter()
                ))
                .build();
        return logbook;
    }
}
