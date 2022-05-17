package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishDto;

import javax.validation.Valid;

public interface FullDish {

    @Valid
    DishDto.Base.Full getDish();
}
