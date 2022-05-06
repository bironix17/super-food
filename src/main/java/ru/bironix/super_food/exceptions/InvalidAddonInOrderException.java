package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class InvalidAddonInOrderException extends RuntimeException {

    private List<Integer> invalidDishesIds;

    public InvalidAddonInOrderException(List<Integer> invalidDishesIds) {
        super(ApiError.INCORRECT_DATA_FOR_ADDON.name());
        this.invalidDishesIds = invalidDishesIds;
    }
}
