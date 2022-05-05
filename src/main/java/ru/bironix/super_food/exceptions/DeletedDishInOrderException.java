package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class DeletedDishInOrderException extends RuntimeException {

    private List<Integer> deletedIds;

    public DeletedDishInOrderException(List<Integer> deletedIds) {
        super(ApiError.DELETED_DISH_IN_ORDER.name());
        this.deletedIds = deletedIds;
    }
}
