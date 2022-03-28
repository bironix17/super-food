package ru.bironix.super_food.models.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Schema(description = "Сжатое блюдо")
@Data
@SuperBuilder
public class SmallDishDto extends AbstractDishDto {

    @Schema(description = "базовая порция блюда, расположена по индексу, хранящейся в baseIndexPortion из portions")
    @NonNull
    PortionDto basePortion;
}
