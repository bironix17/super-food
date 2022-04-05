package ru.bironix.super_food.dtos.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.dish.SmallDishDto;

import java.util.List;

@Schema(description = "акция")
@Data
@SuperBuilder
public class FullActionDto extends AbstractActionDto {

    @Schema(description = "Список блюд по акции")
    @NonNull
    List<SmallDishDto> dishes;

}