package ru.bironix.super_food.dtos.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Категория")
@AllArgsConstructor
public class CategoryDto {

    CategoryTypeDto categoryTypeDto;

    @Schema(nullable = true)
    List<DishDto.Base.Small> dishes;
}
