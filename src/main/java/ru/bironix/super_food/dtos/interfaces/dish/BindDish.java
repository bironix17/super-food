package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishDto;

import javax.validation.Valid;

public interface BindDish {

    @Valid
    DishDto.Bind getDish();
}
