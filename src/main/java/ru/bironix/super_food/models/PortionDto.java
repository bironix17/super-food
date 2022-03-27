package ru.bironix.super_food.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Schema(description = "Сущность порции блюда")
@Data
@Builder
public class PortionDto {

    @NonNull
    Integer id;

    @Schema(description = "размер порции", example = "100 грамм")
    @NonNull
    String size;

    @NonNull
    Integer price;
}
