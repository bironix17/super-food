package ru.bironix.super_food.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Documentation {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Super-food API")
                .description("Создание моделей - https://super-food17.herokuapp.com/view/")
                )
                .tags(
                        List.of(new Tag().externalDocs(new ExternalDocumentation()
                                .description("Создание моделей")
                                .url("/view/")))
                );
    }
}
