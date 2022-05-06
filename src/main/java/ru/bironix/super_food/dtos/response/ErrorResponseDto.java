package ru.bironix.super_food.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "Сущность ошибки")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {

    @Schema(nullable = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String fieldName;

    @Schema(nullable = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String entityName;

    @Schema(nullable = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    List<Integer> ids;

    @Schema(nullable = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    List<?> elements;

    String message;
}
