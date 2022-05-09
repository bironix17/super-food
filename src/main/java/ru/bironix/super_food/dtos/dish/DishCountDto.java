package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.bironix.super_food.dtos.interfaces.Count;
import ru.bironix.super_food.dtos.interfaces.dish.BindDish;

@Schema(description = "Количество блюда")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DishCountDto implements Count, BindDish {
    Integer count;
    DishDto.Bind dish;
}