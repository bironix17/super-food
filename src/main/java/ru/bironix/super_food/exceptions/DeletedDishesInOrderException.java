package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class DeletedDishesInOrderException extends RuntimeException {

    private List<Integer> deletedIds;
    private final ApiError apiError = ApiError.DELETED_DISH_IN_ORDER;


    public DeletedDishesInOrderException(List<Integer> deletedIds) {
        super(ApiError.DELETED_DISH_IN_ORDER.name());
        this.deletedIds = deletedIds;
    }
}
