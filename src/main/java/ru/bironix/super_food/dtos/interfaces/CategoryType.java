package ru.bironix.super_food.dtos.interfaces;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;


public interface CategoryType {

    @Schema(description = "Категория")
    @ApiModelProperty
    @NotNull
    String getCategory();
}
