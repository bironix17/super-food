package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(description = "Сжатое блюдо")
@Data
@SuperBuilder
@NoArgsConstructor
public class SmallDishDto extends AbstractDishDto {
}
