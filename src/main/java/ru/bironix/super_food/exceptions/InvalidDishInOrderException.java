package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.db.models.dish.Dish;

import java.util.List;

@Getter
public class InvalidDishInOrderException extends RuntimeException {

    private List<Dish> invalidDishes;

    public InvalidDishInOrderException(List<Dish> invalidDishes) {
        super(ApiError.INCORRECT_DATA_FOR_DISH.name());
        this.invalidDishes = invalidDishes;
    }
}
