package ru.bironix.super_food.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Super-food API"))
                .externalDocs(new ExternalDocumentation()
                        .description("Порождение моделей")
                        .url("/view/"));
    }

    private static final String authPaths = "/auth/**";


    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("client")
                .pathsToMatch("/client/**", authPaths)
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/admin/**", authPaths)
                .build();
    }

    @Bean
    public GroupedOpenApi deliverymanApi() {
        return GroupedOpenApi.builder()
                .group("deliveryman")
                .pathsToMatch("/deliveryman/**", authPaths)
                .build();
    }

    @Bean
    public GroupedOpenApi cookApi() {
        return GroupedOpenApi.builder()
                .group("cook")
                .pathsToMatch("/cook/**", authPaths)
                .build();
    }

}
