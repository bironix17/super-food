package ru.bironix.super_food.models.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Schema(description = "Полное блюдо")
@Data
@SuperBuilder
public class FullDishDto extends AbstractDishDto {

    @Schema(description = "Описание")
    @NonNull
    String description;

    @Schema(description = "состав блюда", nullable = true)
    String allergens;

    @Schema(description = "Индекс основной порции", defaultValue = "0")
    @Builder.Default
    Integer baseIndexPortion = 0;

    @Schema(description = "набор порций блюда")
    @NonNull
    List<PortionDto> portions;

    @Schema(description = "Возможные добавки", nullable = true)
    List<AddonsDto> addons;

    @Schema(description = "Перечень блюд комбо, не null если category = COMBO", nullable = true)
    List<SmallDishDto> dishes;
}