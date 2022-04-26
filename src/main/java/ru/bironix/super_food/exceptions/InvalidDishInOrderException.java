package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.db.models.dish.Dish;

import java.util.List;

@Getter
public class InvalidDishInOrderException extends RuntimeException {

    private List<Dish> invalidDishes;

    public InvalidDishInOrderException(List<Dish> invalidDishes) {
        super("Некорректное блюдо. Версия блюда на сервере содержит иные значения");
        this.invalidDishes = invalidDishes;
    }
}
