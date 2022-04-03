package ru.bironix.super_food.models.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Schema(description = "Порция блюда")
@Data
@Builder
public class PortionDto {

    @NonNull
    Integer id;

    @Schema(description = "размер порции, null если category = COMBO", example = "100 грамм", nullable = true)
    String size;

    @NonNull
    Integer price;
}
