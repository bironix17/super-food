package ru.bironix.super_food.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.bironix.super_food.models.dish.CategoryTypeDto;
import ru.bironix.super_food.models.dish.SmallDishDto;

import java.util.List;

@Data
@Builder
@Schema(description = "Категория")
public class CategoryDto {

    CategoryTypeDto categoryType;

    List<SmallDishDto> dishes;
}
