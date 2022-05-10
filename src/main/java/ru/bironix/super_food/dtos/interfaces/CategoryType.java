package ru.bironix.super_food.dtos.interfaces;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.bironix.super_food.dtos.dish.CategoryTypeDto;

import javax.validation.constraints.NotNull;


public interface CategoryType {

    @Schema(description = "Категория")
    @ApiModelProperty
    @NotNull
    CategoryTypeDto getCategory();
}
