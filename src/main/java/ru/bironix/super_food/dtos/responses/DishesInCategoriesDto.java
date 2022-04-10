package ru.bironix.super_food.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.bironix.super_food.dtos.dish.CategoryDto;

import java.util.List;

@Schema(description = "Список блюд в категориях")
@Data
@Builder
@AllArgsConstructor
public class DishesInCategoriesDto {

    @NonNull
    List<CategoryDto> categories;
}
