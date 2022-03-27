package ru.bironix.super_food.models.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.models.AddonsDto;
import ru.bironix.super_food.models.PortionDto;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Полное блюдо")
@Data
@SuperBuilder
public class FullDishDto extends AbstractDishDto {

    @Schema(description = "состав блюда")
    @NonNull
    String composition;

    @Schema(description = "состав блюда", nullable = true)
    String allergens = "";

    @Schema(description = "Индекс основной порции")
    Integer baseIndexPortion = 0;

    @Schema(description = "набор порций блюда")
    @NonNull
    List<PortionDto> portions;

    @Schema(description = "Возможные добавки")
    List<AddonsDto> addons = new ArrayList<>();
}