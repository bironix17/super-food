package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
@Schema(description = "Категория")
public class CategoryDto {

    @NonNull
    CategoryType categoryType;

    @Schema(nullable = true)
    List<SmallDishDto> dishes;
}
