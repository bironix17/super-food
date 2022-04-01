package ru.bironix.super_food.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.bironix.super_food.models.dish.CategoryDto;

import java.util.List;

@Schema(description = "Список блюд в категориях")
@Data
@Builder
public class DishesInCategoriesDto {

    @NonNull
    List<CategoryDto> categories;
}
