package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class DeletedAddonsInOrderException extends RuntimeException {

    private List<Integer> deletedIds;
    private final ApiError apiError = ApiError.DELETED_ADDON_IN_ORDER;

    public DeletedAddonsInOrderException(List<Integer> deletedIds) {
        super(ApiError.DELETED_ADDON_IN_ORDER.name());
        this.deletedIds = deletedIds;
    }
}
