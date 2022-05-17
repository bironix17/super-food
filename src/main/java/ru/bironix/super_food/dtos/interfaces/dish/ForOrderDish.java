package ru.bironix.super_food.dtos.interfaces.dish;

import ru.bironix.super_food.dtos.dish.DishDto;

import javax.validation.Valid;

public interface ForOrderDish {

    @Valid
    DishDto.Base.ForOrder getDish();
}
