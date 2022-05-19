package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface BindDishForOrder {
    @Valid
    @NotNull
    DishDto.BindForOrder getDish();
}
