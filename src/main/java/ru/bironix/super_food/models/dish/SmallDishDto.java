package ru.bironix.super_food.models.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.models.PortionDto;

@Schema(description = "Сущность сжатого блюда")
@Data
@SuperBuilder
public class SmallDishDto extends AbstractDishDto {

    @Schema(description = "базовая порция блюда, расположена по индексу, хранящейся в baseIndexPortion из portions")
    PortionDto basePortion;
}
