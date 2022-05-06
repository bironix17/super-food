package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class InvalidEntitiesOrderException extends RuntimeException {

    private List<Integer> invalidEntitiesIds;
    private final ApiError apiError;

    public InvalidEntitiesOrderException(List<Integer> invalidEntitiesIds, ApiError apiError) {
        super(apiError.name());
        this.invalidEntitiesIds = invalidEntitiesIds;
        this.apiError = apiError;
    }
}
