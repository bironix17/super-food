package ru.bironix.super_food.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.bironix.super_food.dtos.dish.SmallDishDto;

import java.util.List;

@Schema(description = "Список блюд")
@Data
@Builder
@AllArgsConstructor
public class DishesResponseDto {
    List<SmallDishDto> dishes;
}
