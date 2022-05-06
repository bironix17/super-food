package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;
import ru.bironix.super_food.db.models.dish.Dish;

import java.util.List;

@Getter
public class InvalidDishInOrderException extends RuntimeException {

    private List<Integer> invalidDishesIds;

    public InvalidDishInOrderException(List<Integer> invalidDishesIds) {
        super(ApiError.INCORRECT_DATA_FOR_DISH.name());
        this.invalidDishesIds = invalidDishesIds;
    }
}
